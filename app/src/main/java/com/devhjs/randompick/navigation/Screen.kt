package com.devhjs.randompick.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object List : Screen("list")
}