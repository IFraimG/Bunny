package org.xmis.bunny.presentation.ui.files.state

data class FileItem(
    val path: String,
    val fullName: String,
    val fileName: String,
    val fileExtension: String,
)