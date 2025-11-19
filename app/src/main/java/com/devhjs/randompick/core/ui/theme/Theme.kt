package com.devhjs.randompick.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = SeedPrimary,
    onPrimary = Color.White,
    primaryContainer = SeedPrimaryLight,
    onPrimaryContainer = SeedPrimaryDark,

    secondary = SeedSecondary,
    onSecondary = Color.White,
    secondaryContainer = SeedSecondaryLight,
    onSecondaryContainer = SeedSecondaryDark,

    tertiary = Color(0xFF8B5CF6),
    onTertiary = Color.White,
    tertiaryContainer = SeedInfo,
    onTertiaryContainer = SeedPrimaryDark,

    background = LightBackgroundBase,
    onBackground = Color(0xFF1C1B1F),

    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Gray500,

    error = SeedError,
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF7F1D1D),

    outline = Color(0xFFE5E7EB),
    outlineVariant = Color(0xFFD1D5DB),

    surfaceTint = SeedPrimary,
    inverseSurface = Gray800,
    inverseOnSurface = Gray100,
    inversePrimary = SeedPrimaryLight,

    scrim = Color.Black.copy(alpha = 0.5f)
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = Color(0xFF004A94),
    onPrimaryContainer = Color(0xFFD6E3FF),

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = Color(0xFF005043),
    onSecondaryContainer = Color(0xFF9CF3D9),

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkSurface,
    onSurface = DarkTextPrimary,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,

    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),

    outline = DarkOutline,
    outlineVariant = Color(0xFF444746)
)



@Composable
fun RandomPickTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}