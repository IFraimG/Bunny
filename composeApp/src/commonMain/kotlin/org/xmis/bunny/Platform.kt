package org.xmis.bunny

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform