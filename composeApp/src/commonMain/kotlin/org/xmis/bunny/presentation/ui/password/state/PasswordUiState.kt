package org.xmis.bunny.presentation.ui.password.state

import org.xmis.bunny.data.storages.entities.PasswordEntity

data class PasswordUiState(
    val passwordsList: List<PasswordEntity> = mutableListOf()
)