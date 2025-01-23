package com.yugentech.shuki.ui.theme

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

private val Pure_Black = Color(0xFF000000)
private val Dark_Gray = Color(0xFF1A1A1A)
private val Medium_Dark_Gray = Color(0xFF2D2D2D)
private val Gray = Color(0xFF4D4D4D)
private val Light_Gray = Color(0xFF808080)
private val Very_Light_Gray = Color(0xFFD3D3D3)
private val Pure_White = Color(0xFFFFFFFF)

private val MonochromeDarkColorScheme = darkColorScheme(
    primary = Pure_White,
    onPrimary = Pure_Black,
    primaryContainer = Medium_Dark_Gray,
    onPrimaryContainer = Very_Light_Gray,

    secondary = Light_Gray,
    onSecondary = Pure_Black,
    secondaryContainer = Dark_Gray,
    onSecondaryContainer = Very_Light_Gray,

    tertiary = Gray,
    onTertiary = Pure_White,
    tertiaryContainer = Medium_Dark_Gray,
    onTertiaryContainer = Very_Light_Gray,

    background = Pure_Black,
    onBackground = Pure_White,

    surface = Dark_Gray,
    onSurface = Pure_White,
    surfaceVariant = Medium_Dark_Gray,
    onSurfaceVariant = Very_Light_Gray,

    error = Color(0xFFD3D3D3),
    onError = Pure_Black,
    errorContainer = Gray,
    onErrorContainer = Pure_White,

    outline = Light_Gray,
    outlineVariant = Gray,
    scrim = Pure_Black.copy(alpha = 0.4f),
    inverseSurface = Very_Light_Gray,
    inverseOnSurface = Pure_Black,
    inversePrimary = Dark_Gray
)

private val MonochromeLightColorScheme = lightColorScheme(
    primary = Pure_Black,
    onPrimary = Pure_White,
    primaryContainer = Light_Gray,
    onPrimaryContainer = Pure_Black,

    secondary = Gray,
    onSecondary = Pure_White,
    secondaryContainer = Very_Light_Gray,
    onSecondaryContainer = Pure_Black,

    tertiary = Medium_Dark_Gray,
    onTertiary = Pure_White,
    tertiaryContainer = Light_Gray,
    onTertiaryContainer = Pure_Black,

    background = Pure_White,
    onBackground = Pure_Black,

    surface = Very_Light_Gray,
    onSurface = Pure_Black,
    surfaceVariant = Light_Gray,
    onSurfaceVariant = Dark_Gray,

    error = Gray,
    onError = Pure_White,
    errorContainer = Light_Gray,
    onErrorContainer = Pure_Black,

    outline = Gray,
    outlineVariant = Light_Gray,
    scrim = Pure_Black.copy(alpha = 0.2f),
    inverseSurface = Dark_Gray,
    inverseOnSurface = Pure_White,
    inversePrimary = Very_Light_Gray
)

@Composable
fun ShukiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> MonochromeDarkColorScheme
        else -> MonochromeLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}