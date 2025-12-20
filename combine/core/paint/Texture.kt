package top.fifthlight.combine.paint

import androidx.compose.runtime.Immutable
import top.fifthlight.data.*

@Immutable
abstract class Texture(
    override val size: IntSize,
    override val padding: IntPadding = IntPadding.ZERO,
) : Drawable {
    abstract fun Canvas.draw(
        dstRect: Rect,
        tint: Color = Colors.WHITE,
        srcRect: Rect,
    )

    fun Canvas.draw(
        dstRect: IntRect,
        tint: Color = Colors.WHITE,
        srcRect: IntRect,
    ) {
        draw(
            dstRect = dstRect.toRect(),
            tint = tint,
            srcRect = srcRect.toRect(),
        )
    }
}

@Immutable
abstract class BackgroundTexture(
    override val size: IntSize,
) : Drawable {
    override val padding: IntPadding
        get() = IntPadding.ZERO

    fun Canvas.draw(dstRect: IntRect, tint: Color = Colors.WHITE, scale: Float) = draw(dstRect.toRect(), tint, scale)
    abstract fun Canvas.draw(dstRect: Rect, tint: Color = Colors.WHITE, scale: Float)
}
