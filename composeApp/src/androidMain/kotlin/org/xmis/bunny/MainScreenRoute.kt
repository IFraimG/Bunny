package org.xmis.bunny.presentation.ui.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import org.xmis.bunny.presentation.ui.files.FilesListViewModel
import org.xmis.bunny.presentation.ui.password.PasswordViewModel
import xmis.bunny.AppLogger.AppLogger

@Composable
actual fun MainScreenRoute(navController: NavController) {
    val fileViewModel = koinViewModel<FilesListViewModel>()
    val viewModel = koinViewModel<PasswordViewModel>()


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            uri?.let {
//                content://com.android.providers.media.documents/document/image
                fileViewModel.uploadFile(uri.toString())
                AppLogger.i("msg", uri.toString())
                AppLogger.i("msg", uri.toString())
                AppLogger.i("msg", uri.toString())
                AppLogger.i("msg", uri.toString())
//                fileViewModel.uploadFiles(it)
            }
        }
    fun loadFile() {
        launcher.launch(arrayOf("application/pdf", "image/*"))
    }

    MainScreen(navController = navController,
        passwordViewModule = viewModel,
        launchUpload = {
            loadFile()
        })

}