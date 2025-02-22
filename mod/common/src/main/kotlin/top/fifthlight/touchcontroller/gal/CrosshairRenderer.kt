package top.fifthlight.touchcontroller.gal

import top.fifthlight.combine.paint.Canvas
import top.fifthlight.touchcontroller.config.TouchRingConfig

interface CrosshairRenderer {
    fun renderOuter(canvas: Canvas, config: TouchRingConfig)
    fun renderInner(canvas: Canvas, config: TouchRingConfig, progress: Float)
}
