package top.fifthlight.combine.widget.ui

import androidx.compose.runtime.*
import top.fifthlight.combine.data.LocalTextFactory
import top.fifthlight.combine.data.Text
import top.fifthlight.combine.layout.Layout
import top.fifthlight.combine.modifier.Constraints
import top.fifthlight.combine.modifier.Modifier
import top.fifthlight.combine.modifier.drawing.border
import top.fifthlight.combine.modifier.drawing.rotate
import top.fifthlight.combine.modifier.placement.*
import top.fifthlight.combine.modifier.pointer.clickable
import top.fifthlight.combine.modifier.pointer.consumePress
import top.fifthlight.combine.node.LocalTextMeasurer
import top.fifthlight.combine.paint.Colors
import top.fifthlight.combine.ui.style.ColorTheme
import top.fifthlight.combine.ui.style.LocalColorTheme
import top.fifthlight.combine.ui.style.LocalTextStyle
import top.fifthlight.combine.ui.style.TextStyle
import top.fifthlight.combine.widget.base.Popup
import top.fifthlight.combine.widget.base.layout.Box
import top.fifthlight.combine.widget.base.layout.Row
import top.fifthlight.combine.widget.base.layout.RowScope
import top.fifthlight.data.IntRect
import top.fifthlight.data.IntSize
import top.fifthlight.touchcontroller.assets.Textures
import kotlin.math.max

@Composable
fun DropdownMenuIcon(expanded: Boolean) {
    Text(
        text = "▶",
        modifier = Modifier.rotate(if (expanded) -90f else 90f)
    )
}

@JvmName("DropdownMenuListString")
@Composable
fun <T> DropdownMenuBoxScope.DropdownMenuList(
    modifier: Modifier = Modifier,
    items: List<T>,
    stringProvider: (T) -> String,
    selectedIndex: Int = -1,
    onItemSelected: (Int) -> Unit = {},
) {
    val textFactory = LocalTextFactory.current
    DropdownMenuList(
        modifier = modifier,
        items = items,
        textProvider = { textFactory.literal(stringProvider(it)) },
        selectedIndex = selectedIndex,
        onItemSelected = onItemSelected,
    )
}

@Composable
fun <T> DropdownMenuBoxScope.DropdownMenuList(
    modifier: Modifier = Modifier,
    items: List<T>,
    textProvider: (T) -> Text,
    selectedIndex: Int = -1,
    onItemSelected: (Int) -> Unit = {},
) {
    val windowTexture = Textures.GUI_WIDGET_SELECT_FLOAT_WINDOW
    val itemTexture = Textures.GUI_WIDGET_SELECT_LIST_GRAY
    val itemTextureSelected = Textures.GUI_WIDGET_SELECT_LIST_LIGHT
    val itemTextureWidth = itemTexture.padding.width
    val itemTextureHeight = itemTexture.padding.height
    val textMeasurer = LocalTextMeasurer.current
    Layout(
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            var itemWidth = anchor.size.width - windowTexture.padding.width
            var itemHeight = 0
            var itemHeights = IntArray(measurables.size)
            for ((index, item) in items.withIndex()) {
                val textSize = textMeasurer.measure(textProvider(item))
                val width = textSize.width + itemTextureWidth
                val height = textSize.height + itemTextureHeight
                itemHeights[index] = height
                itemWidth = max(width, itemWidth)
                itemHeight += height
            }

            val width = itemWidth.coerceIn(constraints.minWidth, constraints.maxWidth)
            val height = itemHeight.coerceIn(constraints.minHeight, constraints.maxHeight)

            val placeables = measurables.mapIndexed { index, measurable ->
                measurable.measure(
                    Constraints(
                        minWidth = width,
                        maxWidth = width,
                        minHeight = itemHeights[index],
                        maxHeight = itemHeights[index],
                    )
                )
            }
            layout(width, height) {
                var yPos = 0
                placeables.forEachIndexed { index, placeable ->
                    placeable.placeAt(0, yPos)
                    yPos += placeable.height
                }
            }
        },
    ) {
        for ((index, item) in items.withIndex()) {
            val text = textProvider(item)
            Text(
                modifier = Modifier
                    .border(
                        if (index == selectedIndex) {
                            itemTextureSelected
                        } else {
                            itemTexture
                        }
                    )
                    .clickable {
                        onItemSelected(index)
                    },
                color = if (index == selectedIndex) {
                    Colors.BLACK
                } else {
                    Colors.WHITE
                },
                text = text,
            )
        }
    }
}

interface DropdownMenuBoxScope {
    val anchor: IntRect
}

private fun DropdownMenuBoxScope(anchor: IntRect) = object : DropdownMenuBoxScope {
    override val anchor: IntRect = anchor
}

@Composable
fun DropdownMenuBox(
    modifier: Modifier = Modifier,
    colorTheme: ColorTheme? = null,
    textStyle: TextStyle? = null,
    expanded: Boolean = false,
    onExpandedChanged: (Boolean) -> Unit,
    dropDownContent: @Composable DropdownMenuBoxScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    var anchor by remember { mutableStateOf<IntRect?>(null) }
    val colorTheme = colorTheme ?: ColorTheme.light
    val textStyle = textStyle ?: LocalTextStyle.current.copy(
        //shadow = true,
    )

    @Composable
    fun ContentStyle(content: @Composable () -> Unit) {
        CompositionLocalProvider(
            LocalColorTheme provides colorTheme,
            LocalTextStyle provides textStyle,
            content = content,
        )
    }

    Row(
        modifier = Modifier
            .border(Textures.GUI_WIDGET_SELECT_SELECT)
            .anchor {
                anchor = it
            }
            .clickable {
                onExpandedChanged(!expanded)
            }
            .then(modifier),
    ) {
        ContentStyle {
            content()
        }
    }

    val currentAnchor = anchor
    if (expanded && currentAnchor != null) {
        val scope = DropdownMenuBoxScope(currentAnchor)
        Popup(
            onDismissRequest = {
                onExpandedChanged(false)
            }
        ) {
            var screenSize by remember { mutableStateOf<IntSize?>(null) }
            var contentSize by remember { mutableStateOf(IntSize.ZERO) }
            val currentScreenSize = screenSize ?: IntSize.ZERO
            val top = if (currentAnchor.bottom + contentSize.height > currentScreenSize.height) {
                currentAnchor.top - contentSize.height
            } else {
                currentAnchor.bottom
            }
            val left = if (currentAnchor.left + contentSize.width > currentScreenSize.width) {
                currentScreenSize.width - contentSize.width
            } else {
                currentAnchor.left
            }

            Layout(
                modifier = Modifier
                    .fillMaxSize()
                    .onPlaced { screenSize = it.size },
                measurePolicy = { measurables, _ ->
                    val constraints = Constraints()
                    val placeables = measurables.map { it.measure(constraints) }
                    val width = placeables.maxOfOrNull { it.width } ?: 0
                    val height = placeables.maxOfOrNull { it.height } ?: 0
                    layout(width, height) {
                        placeables.forEach { it.placeAt(left, top) }
                    }
                }
            ) {
                screenSize?.let { screenSize ->
                    Box(
                        modifier = Modifier
                            .border(Textures.GUI_WIDGET_SELECT_FLOAT_WINDOW)
                            .minWidth(currentAnchor.size.width - 2)
                            .maxHeight(screenSize.height / 2)
                            .onPlaced { contentSize = it.size }
                            .consumePress()
                    ) {
                        ContentStyle {
                            dropDownContent(scope)
                        }
                    }
                }
            }
        }
    }
}