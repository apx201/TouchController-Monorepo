package top.fifthlight.touchcontroller.ui.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.collections.immutable.persistentListOf
import top.fifthlight.combine.data.Identifier
import top.fifthlight.combine.data.Text
import top.fifthlight.touchcontroller.ui.tab.general.ControlTab
import top.fifthlight.touchcontroller.ui.tab.general.DebugTab
import top.fifthlight.touchcontroller.ui.tab.general.RegularTab
import top.fifthlight.touchcontroller.ui.tab.general.TouchRingTab
import top.fifthlight.touchcontroller.ui.tab.layout.CustomControlLayoutTab
import top.fifthlight.touchcontroller.ui.tab.layout.GuiControlLayoutTab
import top.fifthlight.touchcontroller.ui.tab.layout.ManageControlPresetsTab

data class TabOptions(
    private val titleId: Identifier,
    val group: TabGroup? = null,
    val index: Int,
    val openAsScreen: Boolean = false,
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
    ManageControlPresetsTab,
    CustomControlLayoutTab,
    GuiControlLayoutTab,
    RegularTab,
    ControlTab,
    TouchRingTab,
    DebugTab,
    ItemTabs.usableItemsTab,
    ItemTabs.showCrosshairItemsTab,
    ItemTabs.crosshairAimingItemsTab,
)
