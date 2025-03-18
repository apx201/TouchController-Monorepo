package top.fifthlight.touchcontroller.common.layout

import top.fifthlight.combine.paint.Color
import top.fifthlight.touchcontroller.common.control.ForwardButton

fun Context.ForwardButton(config: ForwardButton) {
    val (_, clicked, _) = Button(id = config.id) { clicked ->
        withAlign(align = Align.CENTER_CENTER, size = size) {
            if (config.classic) {
                if (clicked) {
                    Texture(config.textureSet.textureSet.up, tint = Color(0xFFAAAAAAu))
                } else {
                    Texture(config.textureSet.textureSet.up)
                }
            } else {
                if (clicked) {
                    Texture(config.textureSet.textureSet.upActive)
                } else {
                    Texture(config.textureSet.textureSet.up)
                }
            }
        }
    }
    if (clicked) {
        result.forward = 1f
    }
}