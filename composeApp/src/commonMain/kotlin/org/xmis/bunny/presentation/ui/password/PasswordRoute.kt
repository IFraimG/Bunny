package org.xmis.bunny.presentation.ui.password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.xmis.bunny.presentation.models.PasswordData
import org.xmis.bunny.presentation.models.PasswordExtended
import org.xmis.bunny.presentation.ui.password.state.PasswordListener
import org.xmis.bunny.presentation.ui.password.state.SideEffect
import org.xmis.bunny.presentation.utils.ObserveAsEvent
import org.xmis.bunny.presentation.utils.localeSnackbarState

@Composable
fun PasswordRoute() {
    val viewModel = koinViewModel<PasswordViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getAllPasswords()

    val snackBarState = localeSnackbarState.current

    val listener = object : PasswordListener {
        override fun showItem(passwordID: Long): String {
            return viewModel.showPassword(passwordID)
        }

        override fun deleteItem(passwordID: Long) {
            val passwordItem: PasswordExtended? =
                uiState.passwordsList.find { item -> item.id == passwordID }
            if (passwordItem != null) {
                viewModel.deletePassword(passwordItem)
            }
        }

        override fun changeItem(password: PasswordExtended) {
            viewModel.updatePassword(password)
        }
    }

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is SideEffect.FailUpdate -> {
                snackBarState.showSnackbar(
                    message = sideEffect.message ?: ""
                )
            }

            SideEffect.SuccessUpdate -> {
                snackBarState.showSnackbar(
                    message = "Пароль успешно изменен!"
                )
            }
        }
    }

    PasswordScreen(uiState = uiState, actions = listener)
}