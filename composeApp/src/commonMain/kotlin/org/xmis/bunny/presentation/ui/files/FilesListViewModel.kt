package org.xmis.bunny.presentation.ui.files

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.xmis.bunny.data.storages.files.FileStorage
import org.xmis.bunny.files.PlatformFile
import org.xmis.bunny.platform.PlatformContext
import org.xmis.bunny.presentation.ui.files.state.FileItem
import org.xmis.bunny.presentation.ui.files.state.FilesListUIState
import xmis.bunny.AppLogger.AppLogger

class FilesListViewModel(
    private val context: PlatformContext
): ViewModel() {
    private val _uiState = MutableStateFlow(FilesListUIState())
    val uiState: StateFlow<FilesListUIState> = _uiState.asStateFlow()

    private val fileHandler: FileStorage by lazy {
        FileStorage(pathFolder = context.getFilesDirPath())
    }

    //    private val getFilesListUseCase: GetFilesListUseCase by inject()


    fun getFiles() {

        val files: List<PlatformFile> = fileHandler.loadFilesFromFolder()
        val listFileItems: List<FileItem> = files.map { fileHandler.getFileInfo(file = it) }

        _uiState.update {
            it.copy(filesList = listFileItems)
        }

//        AppLogger.i("msg", files.size.toString())
//        AppLogger.i("msg", files[0].toString())
//        AppLogger.i("msg", files[0].toString())
//        AppLogger.i("msg", files.size.toString())
//        AppLogger.i("msg", context.getFilesDirPath())
//        AppLogger.i("msg", context.getFilesDirPath())
    }

    fun uploadFile(uri: String) {
        context.copyFile(uri)
    }
}