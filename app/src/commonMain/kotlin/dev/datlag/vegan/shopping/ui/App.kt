package dev.datlag.vegan.shopping.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.rememberWindowInsetsController
import com.moriatsushi.insetsx.safeDrawingPadding
import dev.datlag.vegan.shopping.ui.theme.*
import org.kodein.di.DI

val LocalDarkMode = compositionLocalOf<Boolean> { error("No dark mode state provided") }
val LocalLifecycleOwner = compositionLocalOf<LifecycleOwner> {
    object : LifecycleOwner {
        override val lifecycle: Lifecycle = LifecycleRegistry()
    }
}

@OptIn(ExperimentalSoftwareKeyboardApi::class)
@Composable
fun App(
    di: DI,
    systemDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val windowInsetsController = rememberWindowInsetsController()

    LaunchedEffect(systemDarkTheme) {
        windowInsetsController?.apply {
            setStatusBarContentColor(dark = !systemDarkTheme)
            setNavigationBarsContentColor(dark = !systemDarkTheme)
        }
    }

    CompositionLocalProvider(
        LocalDarkMode provides systemDarkTheme
    ) {
        MaterialTheme(
            colorScheme = if (systemDarkTheme) Colors.getDarkScheme() else Colors.getLightScheme(),
            typography = ManropeTypography()
        ) {
            androidx.compose.material.MaterialTheme(
                colors = MaterialTheme.colorScheme.toLegacyColors(systemDarkTheme),
                shapes = MaterialTheme.shapes.toLegacyShapes(),
                typography = ManropeTypographyLegacy()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    content()
                }
            }
        }
    }
}