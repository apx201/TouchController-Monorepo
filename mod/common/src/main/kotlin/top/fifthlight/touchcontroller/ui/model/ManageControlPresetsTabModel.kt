package top.fifthlight.touchcontroller.ui.model

import org.koin.core.component.inject
import top.fifthlight.touchcontroller.config.GlobalConfigHolder
import top.fifthlight.touchcontroller.config.preset.PresetConfig
import top.fifthlight.touchcontroller.ext.mapState

class ManageControlPresetsTabModel : TouchControllerScreenModel() {
    private val globalConfigHolder: GlobalConfigHolder by inject()
    val presetConfig = globalConfigHolder.config.mapState {
        when (val preset = it.preset) {
            is PresetConfig.BuiltIn -> preset
            is PresetConfig.Custom -> null
        }
    }

    fun update(config: PresetConfig.BuiltIn) {
        globalConfigHolder.updateConfig {
            copy(preset = config)
        }
    }
}