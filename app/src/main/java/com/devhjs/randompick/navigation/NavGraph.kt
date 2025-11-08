package com.devhjs.randompick.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devhjs.randompick.feature.list.ui.ListScreen
import com.devhjs.randompick.feature.main.ui.MainScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) { MainScreen() }
        composable(Screen.List.route) { ListScreen() }
    }
}