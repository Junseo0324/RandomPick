package com.devhjs.randompick.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.devhjs.randompick.navigation.data.BottomNavItem
import com.devhjs.randompick.navigation.data.Screen

@Composable
fun BottomNavBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Main.route,
            { Icon(Icons.Outlined.Home, contentDescription = null) },
            "메인"
        ),
        BottomNavItem(
            Screen.List.route,
            { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
            "리스트"
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
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
                icon = {
                    Icon(
                        imageVector = when (item.route) {
                            Screen.Main.route -> Icons.Outlined.Home
                            Screen.List.route -> Icons.AutoMirrored.Filled.List
                            else -> Icons.Outlined.Home
                        },
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        item.label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}