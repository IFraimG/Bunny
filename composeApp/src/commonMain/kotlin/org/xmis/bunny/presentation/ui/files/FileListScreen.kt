package org.xmis.bunny.presentation.ui.files

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.xmis.bunny.presentation.ui.files.state.FilesListUIState

@Composable
fun FileListScreen(uiState: FilesListUIState) {

    LazyColumn {
        items(uiState.filesList.size) { index ->
            Text(text = uiState.filesList[index].fullName)
        }
    }
}