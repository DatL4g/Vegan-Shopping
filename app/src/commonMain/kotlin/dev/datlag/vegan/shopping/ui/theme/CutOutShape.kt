package dev.datlag.vegan.shopping.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CutOutShape(
    private val cornerRadius: CornerRadius,
    private val verticalPadding: Float = 0F,
    private val horizontalPadding: Float = 0F
) : Shape {

    constructor(radius: CornerRadius, padding: Float) : this(radius, padding, padding)

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val outlinePath = Path()
        outlinePath.addRect(Rect(Offset.Zero, size))

        val cutoutHeight = size.height - verticalPadding
        val cutoutWidth = size.width - horizontalPadding
        val center = Offset(size.width / 2F, size.height / 2F)

        val cutoutPath = Path()
        cutoutPath.addRoundRect(
            RoundRect(
                Rect(
                    topLeft = center - Offset(
                        cutoutWidth / 2f,
                        cutoutHeight / 2f
                    ),
                    bottomRight = center + Offset(
                        cutoutWidth / 2f,
                        cutoutHeight / 2f
                    )
                ),
                cornerRadius = cornerRadius
            )
        )
        return Outline.Generic(Path.combine(
            PathOperation.Difference,
            outlinePath,
            cutoutPath
        ))
    }
}

@Composable
fun CutOutShape(
    radius: Dp,
    verticalPadding: Dp = 0.dp,
    horizontalPadding: Dp = 0.dp,
    density: Density = LocalDensity.current
) = with(density) {
    CutOutShape(
        CornerRadius(radius.toPx(), radius.toPx()),
        verticalPadding.toPx(),
        horizontalPadding.toPx()
    )
}

@Composable
fun CutOutShape(
    radius: Dp,
    padding: Dp,
    density: Density = LocalDensity.current
) = with(density) {
    CutOutShape(CornerRadius(radius.toPx(), radius.toPx()), padding.toPx())
}