package top.fifthlight.touchcontroller.common.layout

import top.fifthlight.combine.paint.Color
import top.fifthlight.touchcontroller.assets.Textures
import top.fifthlight.touchcontroller.common.control.BoatButton
import top.fifthlight.touchcontroller.common.control.BoatButtonSide.LEFT
import top.fifthlight.touchcontroller.common.control.BoatButtonSide.RIGHT

fun Context.BoatButton(config: BoatButton) {
    val id = when (config.side) {
        LEFT -> "boat_left"
        RIGHT -> "boat_right"
    }
    val (_, clicked) = Button(config.id) { clicked ->
        if (config.classic) {
            if (clicked) {
                Texture(Textures.CONTROL_CLASSIC_DPAD_UP, tint = Color(0xFFAAAAAAu))
            } else {
                Texture(Textures.CONTROL_CLASSIC_DPAD_UP)
            }
        } else {
            if (clicked) {
                Texture(Textures.CONTROL_NEW_DPAD_UP_ACTIVE)
            } else {
                Texture(Textures.CONTROL_NEW_DPAD_UP)
            }
        }
    }
    if (clicked) {
        when (config.side) {
            LEFT -> result.boatLeft = true
            RIGHT -> result.boatRight = true
        }
    }
}