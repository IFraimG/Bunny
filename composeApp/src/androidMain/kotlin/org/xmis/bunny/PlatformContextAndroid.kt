package org.xmis.bunny.platform


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import org.xmis.bunny.domain.utils.generateRandomTitle
import java.io.File

class PlatformContextAndroid(
    private val context: Context
) : PlatformContext {

    override fun getFilesDirPath(): String = context.filesDir.absolutePath

    override fun getCacheDirPath(): String = context.cacheDir.absolutePath

    private fun getOriginalFileName(uri: Uri): String {
        return context.contentResolver.query(
            uri,
            arrayOf(OpenableColumns.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            else null
        } ?: "file"
    }
    override fun copyFile(uri: String) {
//        val file = java.io.File(getFilesDirPath())
//        file.mkdirs()
        val uriOriginal: Uri = uri.toUri()

        context.contentResolver.takePersistableUriPermission(
            uriOriginal,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        val originalName = getOriginalFileName(uriOriginal)
        val extension = originalName.substringAfterLast('.', "")


        val fileName = generateRandomTitle(11)
        val targetFileName = if (extension.isNotEmpty()) "$fileName.$extension" else fileName

        val targetFile = File(getFilesDirPath(), targetFileName)

        context.contentResolver.openInputStream(uriOriginal)?.use { input ->
            targetFile.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: error("Cannot open input stream")

    }
}
