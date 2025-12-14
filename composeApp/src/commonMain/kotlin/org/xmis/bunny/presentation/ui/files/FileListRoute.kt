package org.xmis.bunny.presentation.ui.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FileListRoute() {
    val viewModel = koinViewModel<FilesListViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getFiles()
//    val context = LocalContext.current
//    val folder = File(context.getExternalFilesDir(null), "MyFolder")
//    val files = remember { mutableStateOf(emptyList<File>()) }
//
//    LaunchedEffect(Unit) {
//        files.value = loadFilesFromFolder(folder)
//    }

    FileListScreen(uiState)
}