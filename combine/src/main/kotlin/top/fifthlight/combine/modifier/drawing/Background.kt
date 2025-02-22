package top.fifthlight.combine.modifier.drawing

import top.fifthlight.combine.data.BackgroundTexture
import top.fifthlight.combine.data.Texture
import top.fifthlight.combine.layout.Placeable
import top.fifthlight.combine.modifier.DrawModifierNode
import top.fifthlight.combine.modifier.Modifier
import top.fifthlight.combine.paint.Color
import top.fifthlight.combine.paint.RenderContext
import top.fifthlight.data.IntOffset
import top.fifthlight.data.IntSize
import top.fifthlight.data.Offset
import top.fifthlight.data.Rect

fun Modifier.background(color: Color) = then(ColorBackgroundNode(color))

private data class ColorBackgroundNode(
    val color: Color
) : DrawModifierNode, Modifier.Node<ColorBackgroundNode> {
    override fun RenderContext.renderBefore(node: Placeable) {
        canvas.fillRect(
            offset = IntOffset(0, 0),
            size = IntSize(node.width, node.height),
            color = color
        )
    }
}

fun Modifier.background(texture: Texture) =
    then(TextureBackgroundNode(texture))

private data class TextureBackgroundNode(
    val texture: Texture,
) : DrawModifierNode, Modifier.Node<TextureBackgroundNode> {
    override fun RenderContext.renderBefore(node: Placeable) {
        canvas.drawTexture(
            texture = texture,
            dstRect = Rect(offset = Offset.ZERO, size = node.size.toSize()),
        )
    }
}

fun Modifier.background(texture: BackgroundTexture, scale: Float = 1f) =
    then(BackgroundTextureBackgroundNode(texture, scale))

private data class BackgroundTextureBackgroundNode(
    val texture: BackgroundTexture,
    val scale: Float,
) : DrawModifierNode, Modifier.Node<TextureBackgroundNode> {
    override fun RenderContext.renderBefore(node: Placeable) {
        canvas.drawBackgroundTexture(
            texture = texture,
            scale = scale,
            dstRect = Rect(offset = Offset.ZERO, size = node.size.toSize()),
        )
    }
}