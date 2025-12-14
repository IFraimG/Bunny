package org.xmis.bunny.presentation.ui.password.state

import org.xmis.bunny.presentation.models.PasswordData
import org.xmis.bunny.presentation.models.PasswordExtended

interface PasswordListener {
    fun deleteItem(passwordID: Long): Unit
    fun showItem(passwordID: Long): String
    fun changeItem(password: PasswordExtended)
}