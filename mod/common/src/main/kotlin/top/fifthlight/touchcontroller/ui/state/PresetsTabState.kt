package top.fifthlight.touchcontroller.ui.state

import top.fifthlight.touchcontroller.config.preset.LayoutPreset
import top.fifthlight.touchcontroller.config.preset.PresetControlInfo
import kotlin.uuid.Uuid

sealed class PresetsTabState {
    data object Empty : PresetsTabState()

    data class Create(
        val name: String = LayoutPreset.DEFAULT_PRESET_NAME,
        val controlInfo: PresetControlInfo = PresetControlInfo(),
    ) : PresetsTabState() {
        fun toPreset() = LayoutPreset(
            name = name,
            controlInfo = controlInfo,
        )
    }

    data class Edit(
        val uuid: Uuid,
        val name: String,
        val controlInfo: PresetControlInfo,
    ) : PresetsTabState() {
        fun edit(preset: LayoutPreset) = preset.copy(
            name = name,
            controlInfo = controlInfo,
        )
    }

    data class Delete(
        val uuid: Uuid,
    ) : PresetsTabState()
}