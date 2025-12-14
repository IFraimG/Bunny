package org.xmis.bunny.data.storages.files

import org.xmis.bunny.files.PlatformFile
import org.xmis.bunny.presentation.ui.files.state.FileItem

class FileStorage(private val pathFolder: String) {
    private val folder: PlatformFile by lazy {
        PlatformFile(path = pathFolder)
    }

    fun loadFilesFromFolder(): List<PlatformFile> {
        if (!folder.exists() || !folder.isDirectory()) return emptyList()
        return folder.listFiles()
    }

    fun getFileInfo(file: PlatformFile): FileItem {
        return FileItem(path = file.getPathFromFile(),
            fullName = file.getFileFullName(),
            fileName = file.getFileName(),
            fileExtension = file.getExtension(),
            )
    }
}
