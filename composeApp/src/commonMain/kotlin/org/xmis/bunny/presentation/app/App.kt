package org.xmis.bunny.presentation.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinContext
import org.xmis.bunny.presentation.navigation.Destinations
import org.xmis.bunny.presentation.navigation.MainNavHost
import org.xmis.bunny.presentation.utils.localeSnackbarState

@Composable
@Preview
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(values = arrayOf(
        localeSnackbarState provides snackbarHostState
    )) {

        MaterialTheme {
            KoinContext {
                val navController = rememberNavController()

                var showContent by remember { mutableStateOf(false) }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(snackbarHostState) {
                                Snackbar(
                                    snackbarData = it,
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = MaterialTheme.colorScheme.onSecondary,
                                    actionColor = MaterialTheme.colorScheme.primary,
                                    dismissActionContentColor = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        },
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            MainNavHost(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                startDestination = Destinations.MAIN_ROUTE
                            )
                        }
                    }
                }
            }
        }
    }
}