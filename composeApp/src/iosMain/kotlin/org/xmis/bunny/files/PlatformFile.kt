package org.xmis.bunny.files

import kotlinx.cinterop.BooleanVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import platform.Foundation.NSFileManager

actual class PlatformFile actual constructor(private val path: String) {

    actual fun exists(): Boolean = NSFileManager.defaultManager.fileExistsAtPath(path)

    actual fun isDirectory(): Boolean {
        memScoped {
            val isDir = alloc<BooleanVar>()
            NSFileManager.defaultManager.fileExistsAtPath(path, isDir.ptr)
            return isDir.value
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun listFiles(): List<PlatformFile> {
        val manager = NSFileManager.defaultManager
        val list = manager.contentsOfDirectoryAtPath(path, null) ?: return emptyList()
        return list.map { PlatformFile("$path/$it") }
    }
}
