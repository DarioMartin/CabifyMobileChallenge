package com.example.cabifymobilechallenge.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DarkPurple,
    primaryVariant = Purple,
    secondary = Green,
    error = Red
)

private val LightColorPalette = lightColors(
    primary = Purple,
    primaryVariant = DarkPurple,
    secondary = Green,
    error = Red
)

@Composable
fun CabifyMobileChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}