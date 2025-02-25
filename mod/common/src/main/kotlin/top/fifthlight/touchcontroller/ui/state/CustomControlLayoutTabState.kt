package top.fifthlight.touchcontroller.ui.state

import top.fifthlight.touchcontroller.config.LayoutLayer
import top.fifthlight.touchcontroller.config.preset.LayoutPreset
import top.fifthlight.touchcontroller.config.preset.PresetsContainer
import top.fifthlight.touchcontroller.control.ControllerWidget
import kotlin.uuid.Uuid

sealed class CustomControlLayoutTabState {
    data object Disabled: CustomControlLayoutTabState()

    data class Enabled(
        val allPresets: PresetsContainer,
        val selectedPresetUuid: Uuid? = null,
        val selectedPreset: LayoutPreset? = null,
        val selectedLayer: LayoutLayer? = null,
        val selectedWidget: ControllerWidget? = null,
        val pageState: PageState = PageState(),
    ): CustomControlLayoutTabState() {
        data class PageState(
            val selectedLayerIndex: Int = 0,
            val selectedWidgetIndex: Int = -1,
            val moveLocked: Boolean = false,
            val showSideBar: Boolean = false,
            val copiedWidget: ControllerWidget? = null,
        )
    }
}