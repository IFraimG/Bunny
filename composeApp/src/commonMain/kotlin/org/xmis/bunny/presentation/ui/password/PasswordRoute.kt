package org.xmis.bunny.presentation.ui.password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.xmis.bunny.presentation.models.PasswordExtended
import org.xmis.bunny.presentation.ui.password.state.PasswordListener

@Composable
fun PasswordRoute() {
    val viewModel = koinViewModel<PasswordViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getAllPasswords()

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
    }

    PasswordScreen(uiState = uiState, actions = listener)
}