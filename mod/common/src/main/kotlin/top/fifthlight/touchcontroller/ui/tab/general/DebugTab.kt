package top.fifthlight.touchcontroller.ui.tab.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.compose.koinInject
import top.fifthlight.combine.data.Text
import top.fifthlight.combine.layout.Arrangement
import top.fifthlight.combine.modifier.Modifier
import top.fifthlight.combine.modifier.drawing.background
import top.fifthlight.combine.modifier.placement.fillMaxHeight
import top.fifthlight.combine.modifier.placement.fillMaxWidth
import top.fifthlight.combine.modifier.placement.padding
import top.fifthlight.combine.modifier.scroll.verticalScroll
import top.fifthlight.combine.widget.base.layout.Column
import top.fifthlight.combine.widget.ui.Text
import top.fifthlight.touchcontroller.assets.BackgroundTextures
import top.fifthlight.touchcontroller.assets.Texts
import top.fifthlight.touchcontroller.config.DebugConfig
import top.fifthlight.touchcontroller.config.GlobalConfigHolder
import top.fifthlight.touchcontroller.ui.component.*
import top.fifthlight.touchcontroller.ui.tab.Tab
import top.fifthlight.touchcontroller.ui.tab.TabGroup
import top.fifthlight.touchcontroller.ui.tab.TabOptions

object DebugTab : Tab() {
    override val options = TabOptions(
        titleId = Texts.SCREEN_CONFIG_GENERAL_DEBUG_TITLE,
        group = TabGroup.GeneralGroup,
        index = 3,
    )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                AppBar(
                    modifier = Modifier.fillMaxWidth(),
                    leading = {
                        BackButton(
                            screenName = Text.translatable(Texts.SCREEN_CONFIG_TITLE),
                            close = true
                        )
                    },
                    title = {
                        Text(Text.translatable(Texts.SCREEN_CONFIG_GENERAL_DEBUG_TITLE))
                    },
                )
            },
            sideBar = {
                SideTabBar(
                    modifier = Modifier.fillMaxHeight(),
                    onTabSelected = {
                        navigator?.replace(it)
                    }
                )
            },
        ) { modifier ->
            Column(
                modifier = Modifier
                    .padding(8)
                    .verticalScroll()
                    .background(BackgroundTextures.BRICK_BACKGROUND)
                    .then(modifier),
                verticalArrangement = Arrangement.spacedBy(8),
            ) {
                val globalConfigHolder: GlobalConfigHolder = koinInject()
                val globalConfig by globalConfigHolder.config.collectAsState()
                fun update(editor: DebugConfig.() -> DebugConfig) {
                    globalConfigHolder.saveConfig(globalConfig.let { config ->
                        config.copy(debug = editor(config.debug))
                    })
                }
                SwitchPreferenceItem(
                    title = Text.translatable(Texts.SCREEN_CONFIG_GENERAL_DEBUG_SHOW_POINTERS_TITLE),
                    description = Text.translatable(Texts.SCREEN_CONFIG_GENERAL_DEBUG_SHOW_POINTERS_DESCRIPTION),
                    value = globalConfig.debug.showPointers,
                    onValueChanged = { update { copy(showPointers = it) } }
                )
                SwitchPreferenceItem(
                    title = Text.translatable(Texts.SCREEN_CONFIG_GENERAL_DEBUG_ENABLE_TOUCH_EMULATION_TITLE),
                    description = Text.translatable(Texts.SCREEN_CONFIG_GENERAL_DEBUG_ENABLE_TOUCH_EMULATION_DESCRIPTION),
                    value = globalConfig.debug.enableTouchEmulation,
                    onValueChanged = { update { copy(enableTouchEmulation = it) } }
                )
            }
        }
    }
}