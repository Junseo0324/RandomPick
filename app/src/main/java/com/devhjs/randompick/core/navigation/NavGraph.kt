package com.devhjs.randompick.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devhjs.randompick.presentation.license.LicenseScreen
import com.devhjs.randompick.presentation.list.ui.ListScreen
import com.devhjs.randompick.presentation.main.ui.MainScreen
import com.devhjs.randompick.core.navigation.data.Screen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None},
        popEnterTransition = { EnterTransition.None},
        popExitTransition = { ExitTransition.None}
        ) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.List.route) { ListScreen() }
        composable(Screen.License.route) { LicenseScreen(navController) }
    }
}