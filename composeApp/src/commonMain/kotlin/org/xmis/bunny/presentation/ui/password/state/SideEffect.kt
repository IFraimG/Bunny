package org.xmis.bunny.presentation.ui.password.state

sealed class SideEffect {
    data object SuccessUpdate : SideEffect()
    data class FailUpdate(val message: String? = null) : SideEffect()
}