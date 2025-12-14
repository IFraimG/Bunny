package org.xmis.bunny.files

expect class PlatformFile(path: String) {
    fun exists(): Boolean
    fun isDirectory(): Boolean
    fun listFiles(): List<PlatformFile>
    fun getFileName(): String
    fun getPathFromFile(): String
    fun getFileFullName(): String
    fun getExtension(): String
}
