package top.fifthlight.touchcontroller.layout

import top.fifthlight.touchcontroller.assets.Textures
import top.fifthlight.touchcontroller.control.HideHudButton

fun Context.HideHudButton(config: HideHudButton) {
    val (newClick) = Button(id = config.id) {
        if (config.classic) {
            Texture(texture = Textures.CONTROL_CLASSIC_HIDE_HUD_HIDE_HUD)
        } else {
            Texture(texture = Textures.CONTROL_NEW_HIDE_HUD_HIDE_HUD)
        }
    }

    if (newClick) {
        result.hideHud = true
    }
}