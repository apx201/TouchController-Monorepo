package top.fifthlight.touchcontroller.ui.tab

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import top.fifthlight.combine.data.Text
import top.fifthlight.combine.layout.Arrangement
import top.fifthlight.combine.modifier.Modifier
import top.fifthlight.combine.modifier.drawing.background
import top.fifthlight.combine.modifier.placement.fillMaxHeight
import top.fifthlight.combine.modifier.placement.fillMaxWidth
import top.fifthlight.combine.modifier.placement.padding
import top.fifthlight.combine.modifier.scroll.verticalScroll
import top.fifthlight.combine.widget.base.layout.Column
import top.fifthlight.combine.widget.ui.Button
import top.fifthlight.combine.widget.ui.Text
import top.fifthlight.touchcontroller.assets.BackgroundTextures
import top.fifthlight.touchcontroller.assets.Texts
import top.fifthlight.touchcontroller.config.GlobalConfigHolder
import top.fifthlight.touchcontroller.config.ItemList
import top.fifthlight.touchcontroller.ui.component.*

object ItemTabs : KoinComponent {
    private val globalConfigHolder: GlobalConfigHolder by inject()

    val usableItemsTab = ItemTab(
        options = TabOptions(
            titleId = Texts.SCREEN_CONFIG_ITEM_USABLE_ITEMS_TITLE,
            group = TabGroup.ItemGroup,
            index = 0,
        ),
        value = globalConfigHolder.config.map { it.item.usableItems },
        onValueChanged = {
            val config = globalConfigHolder.config.value
            globalConfigHolder.saveConfig(config.copy(item = config.item.copy(usableItems = it)))
        }
    )

    val showCrosshairItemsTab = ItemTab(
        options = TabOptions(
            titleId = Texts.SCREEN_CONFIG_ITEM_SHOW_CROSSHAIR_ITEMS_TITLE,
            group = TabGroup.ItemGroup,
            index = 0,
        ),
        value = globalConfigHolder.config.map { it.item.showCrosshairItems },
        onValueChanged = {
            val config = globalConfigHolder.config.value
            globalConfigHolder.saveConfig(config.copy(item = config.item.copy(showCrosshairItems = it)))
        }
    )

    val crosshairAimingItemsTab = ItemTab(
        options = TabOptions(
            titleId = Texts.SCREEN_CONFIG_ITEM_CROSSHAIR_AIMING_ITEMS_TITLE,
            group = TabGroup.ItemGroup,
            index = 0,
        ),
        value = globalConfigHolder.config.map { it.item.crosshairAimingItems },
        onValueChanged = {
            val config = globalConfigHolder.config.value
            globalConfigHolder.saveConfig(config.copy(item = config.item.copy(crosshairAimingItems = it)))
        }
    )
}

class ItemTab(
    override val options: TabOptions,
    val value: Flow<ItemList>,
    val onValueChanged: (ItemList) -> Unit,
) : Tab() {
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
                        Text(options.title)
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
                HorizontalPreferenceItem(
                    title = Text.translatable(Texts.SCREEN_CONFIG_ITEM_WHITELIST_TITLE),
                ) {
                    Button(
                        onClick = {

                        }
                    ) {
                        Text(Text.translatable(Texts.SCREEN_CONFIG_ITEM_EDIT_TITLE))
                    }
                }
                HorizontalPreferenceItem(
                    title = Text.translatable(Texts.SCREEN_CONFIG_ITEM_BLACKLIST_TITLE),
                ) {
                    Button(
                        onClick = {

                        }
                    ) {
                        Text(Text.translatable(Texts.SCREEN_CONFIG_ITEM_EDIT_TITLE))
                    }
                }
                HorizontalPreferenceItem(
                    title = Text.translatable(Texts.SCREEN_CONFIG_ITEM_COMPONENT_TITLE),
                ) {
                    Button(
                        onClick = {

                        }
                    ) {
                        Text(Text.translatable(Texts.SCREEN_CONFIG_ITEM_EDIT_TITLE))
                    }
                }
            }
        }
    }
}