package com.devhjs.randompick.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devhjs.randompick.feature.list.ui.ListScreen
import com.devhjs.randompick.feature.main.ui.MainScreen
import com.devhjs.randompick.navigation.data.Screen

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
    }
}