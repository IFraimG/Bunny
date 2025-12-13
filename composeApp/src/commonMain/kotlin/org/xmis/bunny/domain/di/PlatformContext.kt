package org.xmis.bunny.platform

interface PlatformContext {
    fun getFilesDirPath(): String
    fun getCacheDirPath(): String
    fun copyFile(uri: String)
}