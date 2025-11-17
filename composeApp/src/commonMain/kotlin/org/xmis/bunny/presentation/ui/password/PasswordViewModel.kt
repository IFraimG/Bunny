package org.xmis.bunny.presentation.ui.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.xmis.bunny.data.storages.entities.PasswordEntity
import org.xmis.bunny.domain.usecase.password.DeletePasswordUseCase
import org.xmis.bunny.domain.usecase.password.GetPasswordsUseCase
import org.xmis.bunny.domain.usecase.password.InsertPasswordUseCase
import org.xmis.bunny.presentation.models.PasswordData
import org.xmis.bunny.presentation.ui.password.state.PasswordUiState

class PasswordViewModel: ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow(PasswordUiState())
    val uiState: StateFlow<PasswordUiState> = _uiState.asStateFlow()

    private val insertPasswordUseCase: InsertPasswordUseCase by inject()
    private val getPasswordsUseCase: GetPasswordsUseCase by inject()
    private val deletePasswordUseCase: DeletePasswordUseCase by inject()

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
                _uiState.update {
                    it.copy(passwordsList = result)
                }
            }

        } catch (err: Exception) {
            err.printStackTrace()
        }
    }

    fun deletePassword(passwordData: PasswordEntity) = viewModelScope.launch {
        try {
            deletePasswordUseCase.execute(passwordData)
        } catch (err: Exception) {
            err.printStackTrace()
        }
    }
}