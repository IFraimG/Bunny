package org.xmis.bunny.presentation.ui.password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.xmis.bunny.presentation.models.PasswordExtended
import org.xmis.bunny.presentation.ui.password.components.PasswordItem
import org.xmis.bunny.presentation.ui.password.components.TableHeader


@Composable
@Preview
fun PasswordScreen() {
    val viewModel = koinViewModel<PasswordViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getAllPasswords()

    fun deleteItem(passwordID: Long) {
        val passwordItem: PasswordExtended? = uiState.passwordsList.find { item -> item.id == passwordID }
        if (passwordItem != null) {
            viewModel.deletePassword(passwordItem)
        }
    }

    fun showItem(passwordID: Long): String {
        return viewModel.showPassword(passwordID)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
    ) {
        TableHeader()
        LazyColumn {
            items(uiState.passwordsList) { item ->
                PasswordItem(passwordData = item,
                    deleteItem = { passwordID ->
                        deleteItem(passwordID = passwordID)
                    },
                    showItem = { passwordID -> showItem(passwordID) })
            }
        }

    }
}