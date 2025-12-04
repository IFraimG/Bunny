package org.xmis.bunny.presentation.ui.password.state

interface PasswordListener {
    fun deleteItem(passwordID: Long): Unit
    fun showItem(passwordID: Long): String
}