package dev.datlag.vegan.shopping.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object MaterialSymbols {

    @Composable
    fun rememberEco(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "eco",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(9.083f, 32.542f)
                    quadToRelative(-1.791f, -1.834f, -2.812f, -4.25f)
                    quadToRelative(-1.021f, -2.417f, -1.021f, -5f)
                    quadToRelative(0f, -2.875f, 1.021f, -5.375f)
                    reflectiveQuadToRelative(3.146f, -4.584f)
                    quadToRelative(1.416f, -1.458f, 3.583f, -2.437f)
                    quadToRelative(2.167f, -0.979f, 5.083f, -1.542f)
                    quadToRelative(2.917f, -0.562f, 6.563f, -0.687f)
                    reflectiveQuadToRelative(8.062f, 0.166f)
                    quadToRelative(0.334f, 4.334f, 0.23f, 7.979f)
                    quadToRelative(-0.105f, 3.646f, -0.646f, 6.563f)
                    quadToRelative(-0.542f, 2.917f, -1.563f, 5.146f)
                    reflectiveQuadToRelative(-2.521f, 3.687f)
                    quadToRelative(-2.083f, 2.125f, -4.52f, 3.167f)
                    quadToRelative(-2.438f, 1.042f, -5.105f, 1.042f)
                    quadToRelative(-2.791f, 0f, -5.187f, -0.979f)
                    quadToRelative(-2.396f, -0.98f, -4.313f, -2.896f)
                    close()
                    moveToRelative(4.25f, -0.167f)
                    quadToRelative(1.084f, 0.708f, 2.438f, 1.063f)
                    quadToRelative(1.354f, 0.354f, 2.812f, 0.354f)
                    quadToRelative(2.084f, 0f, 4.105f, -0.875f)
                    quadToRelative(2.02f, -0.875f, 3.687f, -2.584f)
                    quadToRelative(0.958f, -1f, 1.729f, -2.645f)
                    quadToRelative(0.771f, -1.646f, 1.292f, -4f)
                    quadToRelative(0.521f, -2.355f, 0.729f, -5.438f)
                    quadToRelative(0.208f, -3.083f, 0.042f, -6.958f)
                    quadToRelative(-3.292f, -0.084f, -6.188f, 0.041f)
                    reflectiveQuadToRelative(-5.291f, 0.584f)
                    quadToRelative(-2.396f, 0.458f, -4.271f, 1.229f)
                    quadToRelative(-1.875f, 0.771f, -3.042f, 1.979f)
                    quadToRelative(-1.75f, 1.792f, -2.625f, 3.75f)
                    reflectiveQuadToRelative(-0.875f, 3.875f)
                    quadToRelative(0f, 2.125f, 0.875f, 4.188f)
                    quadToRelative(0.875f, 2.062f, 1.958f, 3.229f)
                    quadToRelative(2f, -3.792f, 4.959f, -6.938f)
                    quadToRelative(2.958f, -3.146f, 6.458f, -5.021f)
                    quadToRelative(-3.375f, 2.917f, -5.583f, 6.396f)
                    quadToRelative(-2.209f, 3.479f, -3.209f, 7.771f)
                    close()
                    moveToRelative(0f, 0f)
                    close()
                    moveToRelative(0f, 0f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberTempPreferencesEco(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "temp_preferences_eco",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(19.542f, 10.042f)
                    verticalLineToRelative(-3.5f)
                    quadToRelative(0f, -1.5f, 1.02f, -2.542f)
                    quadToRelative(1.021f, -1.042f, 2.521f, -1.042f)
                    horizontalLineToRelative(3.542f)
                    verticalLineTo(6.5f)
                    quadToRelative(0f, 1.5f, -1.042f, 2.521f)
                    quadToRelative(-1.041f, 1.021f, -2.541f, 1.021f)
                    close()
                    moveToRelative(-10.917f, 10f)
                    quadToRelative(-2.167f, 0f, -3.687f, -1.5f)
                    quadToRelative(-1.521f, -1.5f, -1.521f, -3.667f)
                    verticalLineToRelative(-5.25f)
                    horizontalLineToRelative(5.208f)
                    quadToRelative(2.167f, 0f, 3.687f, 1.542f)
                    quadToRelative(1.521f, 1.541f, 1.521f, 3.708f)
                    verticalLineToRelative(5.167f)
                    close()
                    moveToRelative(8.833f, 15.416f)
                    quadToRelative(-0.375f, -0.375f, -0.375f, -0.896f)
                    quadToRelative(0f, -0.52f, 0.375f, -0.895f)
                    lineToRelative(1.334f, -1.375f)
                    quadToRelative(-0.959f, -1.25f, -1.5f, -2.792f)
                    quadToRelative(-0.542f, -1.542f, -0.542f, -3.25f)
                    quadToRelative(0f, -4.125f, 2.896f, -7.042f)
                    quadToRelative(2.896f, -2.916f, 7.021f, -2.916f)
                    horizontalLineToRelative(9.916f)
                    verticalLineToRelative(9.958f)
                    quadToRelative(0.042f, 4.125f, -2.875f, 7.021f)
                    quadToRelative(-2.916f, 2.896f, -7.041f, 2.896f)
                    quadToRelative(-1.709f, 0f, -3.25f, -0.542f)
                    quadToRelative(-1.542f, -0.542f, -2.792f, -1.5f)
                    lineToRelative(-1.375f, 1.333f)
                    quadToRelative(-0.375f, 0.375f, -0.896f, 0.375f)
                    reflectiveQuadToRelative(-0.896f, -0.375f)
                    close()
                    moveToRelative(9.209f, -1.916f)
                    quadToRelative(3.041f, 0f, 5.145f, -2.146f)
                    quadToRelative(2.105f, -2.146f, 2.105f, -5.188f)
                    verticalLineToRelative(-7.25f)
                    horizontalLineToRelative(-7.209f)
                    quadToRelative(-3.041f, 0f, -5.187f, 2.125f)
                    quadToRelative(-2.146f, 2.125f, -2.146f, 5.167f)
                    quadToRelative(0f, 1.167f, 0.333f, 2.208f)
                    quadToRelative(0.334f, 1.042f, 0.959f, 1.959f)
                    lineToRelative(5.125f, -5.084f)
                    quadToRelative(0.375f, -0.416f, 0.896f, -0.416f)
                    quadToRelative(0.52f, 0f, 0.895f, 0.416f)
                    quadToRelative(0.417f, 0.375f, 0.417f, 0.896f)
                    reflectiveQuadToRelative(-0.417f, 0.896f)
                    lineTo(22.5f, 32.25f)
                    quadToRelative(0.917f, 0.625f, 1.958f, 0.958f)
                    quadToRelative(1.042f, 0.334f, 2.209f, 0.334f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberNestEcoLeaf(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "nest_eco_leaf",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(20f, 33.083f)
                    quadToRelative(-2.417f, 0f, -4.542f, -0.791f)
                    quadToRelative(-2.125f, -0.792f, -3.875f, -2.209f)
                    lineToRelative(-2.541f, 2.542f)
                    quadTo(8.667f, 33f, 8.146f, 33f)
                    reflectiveQuadToRelative(-0.938f, -0.375f)
                    quadToRelative(-0.375f, -0.417f, -0.375f, -0.937f)
                    quadToRelative(0f, -0.521f, 0.375f, -0.896f)
                    lineTo(9.75f, 28.25f)
                    quadToRelative(-1.417f, -1.75f, -2.208f, -3.875f)
                    quadToRelative(-0.792f, -2.125f, -0.792f, -4.542f)
                    quadToRelative(0f, -5.541f, 3.854f, -9.416f)
                    reflectiveQuadTo(20f, 6.542f)
                    horizontalLineToRelative(13.292f)
                    verticalLineToRelative(13.291f)
                    quadToRelative(0f, 5.542f, -3.875f, 9.396f)
                    quadToRelative(-3.875f, 3.854f, -9.417f, 3.854f)
                    close()
                    moveToRelative(0f, -2.625f)
                    quadToRelative(4.417f, 0f, 7.521f, -3.104f)
                    reflectiveQuadToRelative(3.104f, -7.521f)
                    verticalLineTo(9.208f)
                    horizontalLineTo(20f)
                    quadToRelative(-4.417f, 0f, -7.521f, 3.105f)
                    quadToRelative(-3.104f, 3.104f, -3.104f, 7.52f)
                    quadToRelative(0f, 1.875f, 0.583f, 3.542f)
                    quadToRelative(0.584f, 1.667f, 1.667f, 3f)
                    lineToRelative(8.917f, -8.917f)
                    quadToRelative(0.416f, -0.416f, 0.937f, -0.416f)
                    quadToRelative(0.521f, 0f, 0.896f, 0.416f)
                    quadToRelative(0.417f, 0.375f, 0.417f, 0.917f)
                    reflectiveQuadToRelative(-0.417f, 0.958f)
                    lineToRelative(-8.917f, 8.875f)
                    quadToRelative(1.334f, 1.084f, 3f, 1.667f)
                    quadToRelative(1.667f, 0.583f, 3.542f, 0.583f)
                    close()
                }
            }.build()
        }
    }
}