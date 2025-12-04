package org.xmis.bunny.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.xmis.bunny.presentation.ui.main.MainScreen
import org.xmis.bunny.presentation.ui.password.PasswordRoute


@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable(Destinations.MAIN_ROUTE) { MainScreen(navController) }
        composable(Destinations.OPEN_PASSWORDS) { PasswordRoute() }
    }
}

