package org.xmis.bunny.presentation.ui.files.state

data class FilesListUIState(
    val filesList: List<FileItem> = mutableListOf()
)