package com.example.myworkoutprogressapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.Gray,
    secondary = Color.Red,
    tertiary = Color.LightGray,
    background = Color.DarkGray,
)

private val LightColorScheme = lightColorScheme(
    primary = Color.LightGray,
    secondary = Color.Red,
    tertiary = Color.White,
    background = Color.Gray,
    /* Other default colors to override
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MyWorkoutProgressAppTheme(
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}