package com.nextsolutions.sprintsphere_pro.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.nextsolutions.x_o.presentation.theme.ColorScheme
import com.nextsolutions.x_o.presentation.theme.darkColors
import com.nextsolutions.x_o.presentation.theme.lightColors
import com.upnext.notabox.presentation.ui.theme.Spaces

private val DarkColorScheme = darkColors(
    Black,
    White,
    Black,
    White,
    Black,
    Black,
    DarkGray,
    White,
    Color.Gray,
    White,
    Black,
    Color.Transparent,
    White
)

private val LightColorScheme = lightColors(
    White,
    Black,
    White,
    Black,
    White,
    White,
    VioletLight,
    Black,
    Color.Gray,
    Black,
    White,
    White,
    Black
)

val LocalSpaces = staticCompositionLocalOf { Spaces() }

val LocalColors = staticCompositionLocalOf { LightColorScheme }

val LocalTypography = staticCompositionLocalOf { com.upnext.notabox.presentation.ui.theme.Typography() }

object SprintSphereProTheme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: com.upnext.notabox.presentation.ui.theme.Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val spaces: Spaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current
}

@Composable
fun SprintSphereProTheme(
    spaces: Spaces = SprintSphereProTheme.spaces,
    typography: com.upnext.notabox.presentation.ui.theme.Typography = SprintSphereProTheme.typography,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val currentColors = if (isDarkTheme) DarkColorScheme else LightColorScheme

    val rememberedColors = remember { currentColors.copy() }.apply { updateColorsFrom(currentColors) }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (isDarkTheme) Black.toArgb() else VioletLight.toArgb()
            window.navigationBarColor = if (isDarkTheme) Black.toArgb() else VioletLight.toArgb()
        }
    }


    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
    ) {
        ProvideTextStyle(typography.title, content = content)
    }

}