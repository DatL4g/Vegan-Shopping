package dev.datlag.vegan.shopping.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.common.toComposeFont
import dev.icerock.moko.resources.FontResource

fun androidx.compose.material3.ColorScheme.toLegacyColors(isDark: Boolean): Colors {
    return if (isDark) {
        darkColors(
            primary = this.primary,
            primaryVariant = this.primaryContainer,
            secondary = this.secondary,
            secondaryVariant = this.secondaryContainer,
            background = this.background,
            surface = this.surface,
            error = this.error,
            onPrimary = this.onPrimary,
            onSecondary = this.onSecondary,
            onBackground = this.onBackground,
            onSurface = this.onSurface,
            onError = this.onError
        )
    } else {
        lightColors(
            primary = this.primary,
            primaryVariant = this.primaryContainer,
            secondary = this.secondary,
            secondaryVariant = this.secondaryContainer,
            background = this.background,
            surface = this.surface,
            error = this.error,
            onPrimary = this.onPrimary,
            onSecondary = this.onSecondary,
            onBackground = this.onBackground,
            onSurface = this.onSurface,
            onError = this.onError
        )
    }
}

fun androidx.compose.material3.Shapes.toLegacyShapes(): Shapes {
    return Shapes(this.small, this.medium, this.large)
}

val Manrope: FontFamily by lazy(LazyThreadSafetyMode.NONE) {
    FontFamily(
        SharedRes.fonts.Manrope.`extra-light`.toComposeFont(FontWeight.ExtraLight),
        SharedRes.fonts.Manrope.`extra-light-italic`.toComposeFont(FontWeight.ExtraLight, FontStyle.Italic),

        SharedRes.fonts.Manrope.light.toComposeFont(FontWeight.Light),
        SharedRes.fonts.Manrope.`light-italic`.toComposeFont(FontWeight.Light, FontStyle.Italic),

        SharedRes.fonts.Manrope.regular.toComposeFont(FontWeight.Normal),
        SharedRes.fonts.Manrope.`regular-italic`.toComposeFont(FontWeight.Normal, FontStyle.Italic),

        SharedRes.fonts.Manrope.medium.toComposeFont(FontWeight.Medium),
        SharedRes.fonts.Manrope.`medium-italic`.toComposeFont(FontWeight.Medium, FontStyle.Italic),

        SharedRes.fonts.Manrope.`semi-bold`.toComposeFont(FontWeight.SemiBold),
        SharedRes.fonts.Manrope.`semi-bold-italic`.toComposeFont(FontWeight.SemiBold, FontStyle.Italic),

        SharedRes.fonts.Manrope.bold.toComposeFont(FontWeight.Bold),
        SharedRes.fonts.Manrope.`bold-italic`.toComposeFont(FontWeight.Bold, FontStyle.Italic),

        SharedRes.fonts.Manrope.`extra-bold`.toComposeFont(FontWeight.ExtraBold),
        SharedRes.fonts.Manrope.`extra-bold-italic`.toComposeFont(FontWeight.ExtraBold, FontStyle.Italic),
    )
}

@Composable
fun ManropeTypography(): Typography {
    val fontFamily = Manrope

    return remember(fontFamily) {
        Typography(
            displayLarge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 57.sp,
                lineHeight = 64.0.sp,
                letterSpacing = (-0.2).sp,
            ),
            displayMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 45.sp,
                lineHeight = 52.0.sp,
                letterSpacing = 0.0.sp
            ),
            displaySmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                lineHeight = 44.0.sp,
                letterSpacing = 0.0.sp
            ),
            headlineLarge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                lineHeight = 40.0.sp,
                letterSpacing = 0.0.sp
            ),
            headlineMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                lineHeight = 36.0.sp,
                letterSpacing = 0.0.sp
            ),
            headlineSmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                lineHeight = 32.0.sp,
                letterSpacing = 0.0.sp
            ),
            titleLarge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.0.sp,
                letterSpacing = 0.0.sp
            ),
            titleMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.0.sp,
                letterSpacing = 0.2.sp
            ),
            titleSmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.0.sp,
                letterSpacing = 0.1.sp
            ),
            bodyLarge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.0.sp,
                letterSpacing = 0.5.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.0.sp,
                letterSpacing = 0.2.sp
            ),
            bodySmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.0.sp,
                letterSpacing = 0.4.sp,
            ),
            labelLarge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.0.sp,
                letterSpacing = 0.1.sp
            ),
            labelMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.0.sp,
                letterSpacing = 0.5.sp
            ),
            labelSmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.0.sp,
                letterSpacing = 0.5.sp
            )
        )
    }
}

@Composable
fun ManropeTypographyLegacy(): androidx.compose.material.Typography {
    val fontFamily = Manrope

    return remember(fontFamily) {
        androidx.compose.material.Typography(
            defaultFontFamily = fontFamily
        )
    }
}