package top.fifthlight.combine.backend.minecraft_1_21_8

import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.resources.ResourceLocation
import top.fifthlight.combine.paint.Canvas
import top.fifthlight.combine.paint.Color
import top.fifthlight.combine.paint.Texture
import top.fifthlight.combine.theme.vanilla.VanillaDrawableProvider
import top.fifthlight.combine.ui.style.DrawableSet
import top.fifthlight.data.IntPadding
import top.fifthlight.data.IntRect
import top.fifthlight.data.IntSize
import top.fifthlight.data.Rect
import top.fifthlight.mergetools.api.ActualConstructor
import top.fifthlight.mergetools.api.ActualImpl

data class VanillaTexture(
    val resourceLocation: ResourceLocation,
    override val size: IntSize,
    override val padding: IntPadding = IntPadding.ZERO,
): Texture(size, padding) {
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

@ActualImpl(VanillaDrawableProvider::class)
object VanillaDrawableProviderImpl: VanillaDrawableProvider {
    @ActualConstructor
    @JvmStatic
    fun of() = this

    override val buttonDrawableSet = DrawableSet(
        normal = VanillaTexture(
            resourceLocation = ResourceLocation.withDefaultNamespace("widget/button"),
            size = IntSize(150, 20),
            padding = IntPadding(2),
        ),
        focus = VanillaTexture(
            resourceLocation = ResourceLocation.withDefaultNamespace("widget/button_highlighted"),
            size = IntSize(150, 20),
            padding = IntPadding(2),
        ),
        disabled = VanillaTexture(
            resourceLocation = ResourceLocation.withDefaultNamespace("widget/button_disabled"),
            size = IntSize(150, 20),
            padding = IntPadding(2),
        ),
    )
}
