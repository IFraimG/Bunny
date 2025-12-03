package org.xmis.bunny.presentation.models


data class PasswordExtended(
    val id: Long,

    val title: String,

    val password: String,

    val description: String?,

    val isDecoded: Boolean = false
)