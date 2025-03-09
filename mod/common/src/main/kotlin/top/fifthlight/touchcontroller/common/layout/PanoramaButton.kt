package top.fifthlight.touchcontroller.common.layout

import top.fifthlight.touchcontroller.assets.Textures
import top.fifthlight.touchcontroller.common.control.PanoramaButton

fun Context.PanoramaButton(config: PanoramaButton) {
    val (newClick) = Button(id = config.id) {
        if (config.classic) {
            Texture(texture = Textures.CONTROL_CLASSIC_PANORAMA_PANORAMA)
        } else {
            Texture(texture = Textures.CONTROL_NEW_PANORAMA_PANORAMA)
        }
    }

    if (newClick) {
        result.takePanorama = true
    }
}