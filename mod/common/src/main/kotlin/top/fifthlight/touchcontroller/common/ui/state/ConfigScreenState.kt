package top.fifthlight.touchcontroller.common.ui.state

import top.fifthlight.touchcontroller.common.config.GlobalConfig

data class ConfigScreenState(
    val originalConfig: GlobalConfig,
    val config: GlobalConfig = originalConfig,
)