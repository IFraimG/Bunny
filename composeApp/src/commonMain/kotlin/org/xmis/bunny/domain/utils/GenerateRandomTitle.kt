package org.xmis.bunny.domain.utils

fun generateRandomTitle(count: Int): String {
    val alphaNumeric = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return alphaNumeric.shuffled().take(count).joinToString("")
}