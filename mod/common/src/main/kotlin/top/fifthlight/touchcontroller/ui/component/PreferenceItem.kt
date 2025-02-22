package top.fifthlight.touchcontroller.ui.component

import androidx.compose.runtime.Composable
import top.fifthlight.combine.data.Text
import top.fifthlight.combine.layout.Alignment
import top.fifthlight.combine.layout.Arrangement
import top.fifthlight.combine.modifier.Modifier
import top.fifthlight.combine.modifier.placement.fillMaxWidth
import top.fifthlight.combine.modifier.placement.minHeight
import top.fifthlight.combine.paint.Colors
import top.fifthlight.combine.widget.base.layout.Column
import top.fifthlight.combine.widget.base.layout.ColumnScope
import top.fifthlight.combine.widget.base.layout.Row
import top.fifthlight.combine.widget.base.layout.RowScope
import top.fifthlight.combine.widget.ui.IntSlider
import top.fifthlight.combine.widget.ui.Slider
import top.fifthlight.combine.widget.ui.Switch
import top.fifthlight.combine.widget.ui.Text

@Composable
fun VerticalPreferenceItem(
    modifier: Modifier = Modifier,
    title: Text,
    description: Text? = null,
    control: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = Modifier
            .minHeight(24)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(4),
    ) {
        Text(title)
        description?.let { description ->
            Text(
                text = description,
                color = Colors.SECONDARY_WHITE,
            )
        }

        control()
    }
}


@Composable
fun HorizontalPreferenceItem(
    modifier: Modifier = Modifier,
    title: Text,
    description: Text? = null,
    control: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = Modifier
            .minHeight(24)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4)
        ) {
            Text(title)
            description?.let { description ->
                Text(
                    text = description,
                    color = Colors.SECONDARY_WHITE,
                )
            }
        }

        control()
    }
}

@Composable
fun SwitchPreferenceItem(
    modifier: Modifier = Modifier,
    title: Text,
    description: Text? = null,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit,
) {
    HorizontalPreferenceItem(
        modifier = modifier,
        title = title,
        description = description,
    ) {
        Switch(
            value = value,
            onValueChanged = onValueChanged,
        )
    }
}

@Composable
fun SliderPreferenceItem(
    modifier: Modifier = Modifier,
    title: Text,
    description: Text? = null,
    range: ClosedFloatingPointRange<Float>,
    value: Float,
    onValueChanged: (Float) -> Unit,
) {
    VerticalPreferenceItem(
        modifier = modifier,
        title = title,
        description = description,
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
            range = range,
            value = value,
            onValueChanged = onValueChanged,
        )
    }
}


@Composable
fun IntSliderPreferenceItem(
    modifier: Modifier = Modifier,
    title: Text,
    description: Text? = null,
    range: IntRange,
    value: Int,
    onValueChanged: (Int) -> Unit,
) {
    VerticalPreferenceItem(
        modifier = modifier,
        title = title,
        description = description,
    ) {
        IntSlider(
            modifier = Modifier.fillMaxWidth(),
            range = range,
            value = value,
            onValueChanged = onValueChanged,
        )
    }
}