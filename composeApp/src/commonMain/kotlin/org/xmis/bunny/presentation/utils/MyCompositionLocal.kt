package org.xmis.bunny.presentation.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val localeSnackbarState = staticCompositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState not provided")
}
