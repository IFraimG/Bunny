package org.xmis.bunny.presentation.ui.password.state

import org.xmis.bunny.presentation.models.PasswordExtended

data class PasswordUiState(
    val passwordsList: List<PasswordExtended> = mutableListOf()
)