package top.fifthlight.combine.widget.ui

import androidx.compose.runtime.Composable
import top.fifthlight.combine.modifier.Modifier
import top.fifthlight.combine.paint.Drawable
import top.fifthlight.combine.widget.base.Canvas
import top.fifthlight.data.IntRect
import top.fifthlight.data.IntSize

@Composable
fun Icon(
    drawable: Drawable,
    modifier: Modifier = Modifier,
    size: IntSize = drawable.size,
) {
    val sizeAspect = size.width.toFloat() / size.height.toFloat()
    Canvas(
        modifier = modifier,
        measurePolicy = { _, constraints ->
            layout(
                width = size.width.coerceIn(constraints.minWidth, constraints.maxWidth),
                height = size.height.coerceIn(constraints.minHeight, constraints.maxHeight),
            ) {
            }
        }
    ) { node ->
        val nodeAspect = node.width.toFloat() / node.height.toFloat()
        val renderSize = if (nodeAspect > sizeAspect) {
            // Height as base
            if (node.height < size.height) {
                IntSize(
                    width = (node.height * sizeAspect).toInt(),
                    height = node.height,
                )
            } else {
                size
            }
        } else {
            // Width as base
            if (node.width < size.width) {
                IntSize(
                    width = node.width,
                    height = (node.height / sizeAspect).toInt(),
                )
            } else {
                size
            }
        }
        drawable.run {
            draw(
                IntRect(
                    offset = (node.size - renderSize) / 2,
                    size = renderSize,
                )
            )
        }
    }
}
