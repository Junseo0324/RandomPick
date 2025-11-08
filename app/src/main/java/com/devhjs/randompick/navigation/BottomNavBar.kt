package com.devhjs.randompick.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Main.route,
            { Icon(Icons.Default.Home, contentDescription = null) },
            "메인"
        ),
        BottomNavItem(
            Screen.List.route,
            { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
            "리스트"
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Main.route)
                            launchSingleTop = true
                        }
                    }
                },
                icon = item.icon,
                label = { Text(item.label) }
            )
        }
    }
}