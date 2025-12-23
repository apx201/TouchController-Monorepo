package top.fifthlight.combine.backend.minecraft_1_21_8

import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.resources.ResourceLocation
import top.fifthlight.combine.paint.BackgroundTexture
import top.fifthlight.combine.paint.Canvas
import top.fifthlight.combine.paint.Color
import top.fifthlight.combine.paint.Texture
import top.fifthlight.data.IntPadding
import top.fifthlight.data.IntRect
import top.fifthlight.data.IntSize
import top.fifthlight.data.Rect
import top.fifthlight.mergetools.api.ActualConstructor
import top.fifthlight.mergetools.api.ActualImpl

@ActualImpl(Texture::class)
data class TextureImpl(
    val resourceLocation: ResourceLocation,
    override val size: IntSize,
    override val padding: IntPadding = IntPadding.ZERO,
): Texture {
    companion object: Texture.Factory {
        @ActualConstructor
        @JvmStatic
        override fun create(
            namespace: String,
            id: String,
            width: Int,
            height: Int,
            padding: IntPadding,
        ): Texture = TextureImpl(
            resourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, id),
            size = IntSize(width, height),
            padding = padding,
        )
    }

    override fun Canvas.draw(
        dstRect: Rect,
        tint: Color,
        srcRect: Rect,
    ) {
    }

    override fun Canvas.draw(
        dstRect: IntRect,
        tint: Color,
    ) {
        val guiGraphics = (this as CanvasImpl).guiGraphics
        guiGraphics.blitSprite(
            RenderPipelines.GUI_TEXTURED,
            resourceLocation,
            dstRect.offset.x,
            dstRect.offset.y,
            dstRect.size.width,
            dstRect.size.height,
            tint.value,
        )
    }
}

@ActualImpl(BackgroundTexture::class)
data class BackgroundTextureImpl(
    val resourceLocation: ResourceLocation,
    override val size: IntSize,
): BackgroundTexture {
    companion object: BackgroundTexture.Factory {
        @ActualConstructor
        @JvmStatic
        override fun create(
            namespace: String,
            id: String,
            width: Int,
            height: Int,
        ): BackgroundTexture = BackgroundTextureImpl(
            resourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, id),
            size = IntSize(width, height),
        )
    }

    override fun Canvas.draw(
        dstRect: Rect,
        tint: Color,
        scale: Float,
    ) {}

    override fun Canvas.draw(
        dstRect: IntRect,
        tint: Color,
    ) {}
}