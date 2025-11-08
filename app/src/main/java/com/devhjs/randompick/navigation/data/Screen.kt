package com.devhjs.randompick.navigation.data

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object List : Screen("list")
}