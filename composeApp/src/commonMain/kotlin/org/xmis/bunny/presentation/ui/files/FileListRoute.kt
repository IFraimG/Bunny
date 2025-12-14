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

    FileListScreen(uiState)
}