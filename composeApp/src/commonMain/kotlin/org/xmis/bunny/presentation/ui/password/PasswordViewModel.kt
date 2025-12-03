package org.xmis.bunny.presentation.ui.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.ByteString.Companion.toByteString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.xmis.bunny.domain.usecase.password.DeletePasswordUseCase
import org.xmis.bunny.domain.usecase.password.GetPasswordsUseCase
import org.xmis.bunny.domain.usecase.password.InsertPasswordUseCase
import org.xmis.bunny.presentation.models.PasswordData
import org.xmis.bunny.presentation.models.PasswordExtended
import org.xmis.bunny.presentation.ui.password.state.PasswordUiState
import xmis.bunny.AppLogger.AppLogger
import xmis.bunny.krypto.Krypto
import xmis.bunny.krypto.decrypt

class PasswordViewModel: ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow(PasswordUiState())
    val uiState: StateFlow<PasswordUiState> = _uiState.asStateFlow()

    private val insertPasswordUseCase: InsertPasswordUseCase by inject()
    private val getPasswordsUseCase: GetPasswordsUseCase by inject()
    private val deletePasswordUseCase: DeletePasswordUseCase by inject()
    private val krypto: Krypto by inject()

    fun insertPassword(passwordData: PasswordData) = viewModelScope.launch {
        try {
            insertPasswordUseCase.execute(passwordData)
        } catch(err: Exception) {
            err.printStackTrace()
        }
    }

    fun getAllPasswords() = viewModelScope.launch {
        try {
            getPasswordsUseCase.execute().collect { result ->
                val targetArray = result.map { item ->
                    PasswordExtended(id = item.id, title = item.title, description = item.description,
                        password = item.password)
                }

                _uiState.update {
                    it.copy(passwordsList = targetArray)
                }
            }

        } catch (err: Exception) {
            err.printStackTrace()
        }
    }

    fun deletePassword(passwordData: PasswordExtended) = viewModelScope.launch {
        try {
            deletePasswordUseCase.execute(passwordData)
        } catch (err: Exception) {
            err.printStackTrace()
        }
    }

    fun showPassword(passwordID: Long): String {
        try {
            val passwordIndex: Int =
                uiState.value.passwordsList.indexOfFirst { item -> item.id == passwordID }

            if (passwordIndex != -1) {
                val passwordData: PasswordExtended = uiState.value.passwordsList[passwordIndex]

                val passwordResult: String = decrypt(passwordData.password)
                val updatedPasswordData = passwordData.copy(
                    title = passwordData.title,
                    password = passwordResult,
                    description = passwordData.description,
                    id = passwordData.id
                )

                val newListPasswords = uiState.value.passwordsList.toMutableList().apply {
                    this[passwordIndex] = updatedPasswordData
                }.toList()

                _uiState.update {
                    it.copy(passwordsList = newListPasswords)
                }

                return passwordResult
            }
        } catch (err: Exception) {
            err.printStackTrace()
        }
        return ""
    }
}