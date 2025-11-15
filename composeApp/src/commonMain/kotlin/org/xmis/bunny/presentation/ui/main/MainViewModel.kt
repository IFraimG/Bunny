package org.xmis.bunny.presentation.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.xmis.bunny.presentation.models.PasswordData
import org.xmis.bunny.presentation.ui.main.state.MainUiState

class MainViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()


    public fun savePassword(data: PasswordData) {

    }
}