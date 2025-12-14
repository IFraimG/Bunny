package org.xmis.bunny.files

import android.content.ContentResolver
import android.net.Uri
import org.xmis.bunny.domain.utils.generateRandomTitle
import java.io.File
import androidx.core.net.toUri

actual class PlatformFile actual constructor(val path: String) {

    private val file = java.io.File(path)

    actual fun exists(): Boolean = file.exists()

    actual fun isDirectory(): Boolean = file.isDirectory

    actual fun listFiles(): List<PlatformFile> =
        file.listFiles()?.map { PlatformFile(it.absolutePath) } ?: emptyList()

    actual fun getFileFullName(): String = file.name
    actual fun getFileName(): String = file.nameWithoutExtension

    actual fun getPathFromFile(): String = file.absolutePath
    actual fun getExtension(): String = file.extension
}