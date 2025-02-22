package top.fifthlight.touchcontroller.ui.tab

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.collections.immutable.persistentListOf
import top.fifthlight.combine.data.Identifier
import top.fifthlight.combine.data.Text
import top.fifthlight.touchcontroller.ui.tab.general.ControlTab
import top.fifthlight.touchcontroller.ui.tab.general.DebugTab
import top.fifthlight.touchcontroller.ui.tab.general.RegularTab
import top.fifthlight.touchcontroller.ui.tab.general.TouchRingTab

data class TabOptions(
    private val titleId: Identifier,
    val group: TabGroup? = null,
    val index: Int,
) {
    val title: Text
        @Composable
        get() = Text.translatable(titleId)
}

abstract class Tab : Screen {
    abstract val options: TabOptions
}

val allTabs = persistentListOf<Tab>(
    AboutTab,
    RegularTab,
    ControlTab,
    TouchRingTab,
    DebugTab,
    ItemTabs.usableItemsTab,
    ItemTabs.showCrosshairItemsTab,
    ItemTabs.crosshairAimingItemsTab,
)
