package org.xmis.bunny.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.xmis.bunny.presentation.ui.main.MainScreen

@Composable
actual fun MainScreenRoute(navController: NavController) {

    MainScreen(
        navController = navController,
        onUploadClick = { /* iOS picker */ }
    )
}