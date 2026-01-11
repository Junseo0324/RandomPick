package com.devhjs.randompick.core.navigation.data

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object List : Screen("list")
    object License : Screen("license")
}