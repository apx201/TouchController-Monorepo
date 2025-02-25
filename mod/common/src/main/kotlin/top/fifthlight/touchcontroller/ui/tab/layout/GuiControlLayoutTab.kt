package top.fifthlight.touchcontroller.ui.tab.layout

import androidx.compose.runtime.Composable
import org.koin.core.component.KoinComponent
import top.fifthlight.touchcontroller.assets.Texts
import top.fifthlight.touchcontroller.ui.tab.Tab
import top.fifthlight.touchcontroller.ui.tab.TabGroup
import top.fifthlight.touchcontroller.ui.tab.TabOptions

object GuiControlLayoutTab : Tab(), KoinComponent {
    override val options = TabOptions(
        titleId = Texts.SCREEN_CONFIG_LAYOUT_GUI_CONTROL_LAYOUT_TITLE,
        group = TabGroup.LayoutGroup,
        index = 2,
    )

    @Composable
    override fun Content() {

    }
}