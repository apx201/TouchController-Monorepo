package top.fifthlight.touchcontroller.ui.tab.layout.custom

import androidx.compose.runtime.Composable
import top.fifthlight.combine.data.Text
import top.fifthlight.combine.modifier.Modifier
import top.fifthlight.combine.modifier.placement.fillMaxSize
import top.fifthlight.combine.modifier.placement.fillMaxWidth
import top.fifthlight.combine.widget.base.layout.Column
import top.fifthlight.combine.widget.ui.Button
import top.fifthlight.combine.widget.ui.Icon
import top.fifthlight.combine.widget.ui.IconButton
import top.fifthlight.combine.widget.ui.Text
import top.fifthlight.touchcontroller.assets.Texts
import top.fifthlight.touchcontroller.assets.Textures
import top.fifthlight.touchcontroller.ui.component.TabButton

object PresetsTab: CustomTab() {
    @Composable
    override fun Icon() {
        Icon(Textures.ICON_PRESET)
    }

    @Composable
    override fun Content() {
        val (screenModel, uiState, tabsButton, sideBarAtRight) = LocalCustomTabContext.current
        SideBarContainer(
            sideBarAtRight = sideBarAtRight,
            tabsButton = tabsButton,
            actions = {
                val currentPreset = uiState.selectedPreset
                IconButton(
                    onClick = {
                        screenModel.newPreset()
                    }
                ) {
                    Icon(Textures.ICON_ADD)
                }
                IconButton(
                    onClick = {
                        currentPreset?.let(screenModel::newPreset)
                    },
                    enabled = currentPreset != null,
                ) {
                    Icon(Textures.ICON_COPY)
                }
                IconButton(
                    onClick = {},
                    enabled = currentPreset != null,
                ) {
                    Icon(Textures.ICON_CONFIG)
                }
                IconButton(
                    onClick = {
                        uiState.selectedPresetUuid?.let(screenModel::deletePreset)
                    },
                    enabled = currentPreset != null,
                ) {
                    Icon(Textures.ICON_DELETE)
                }
            }
        ) { modifier ->
            SideBarScaffold(
                modifier = modifier,
                title = {
                    Text(Text.translatable(Texts.SCREEN_CONFIG_LAYOUT_CUSTOM_CONTROL_LAYOUT_PRESETS))
                },
                actions = {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {

                        }
                    ) {
                        Text(Text.translatable(Texts.SCREEN_CONFIG_LAYOUT_CUSTOM_CONTROL_LAYOUT_LAYERS_MOVE_UP))
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {

                        }
                    ) {
                        Text(Text.translatable(Texts.SCREEN_CONFIG_LAYOUT_CUSTOM_CONTROL_LAYOUT_LAYERS_MOVE_DOWN))
                    }
                }
            ) {
                Column(Modifier.fillMaxSize()) {
                    for ((uuid, preset) in uiState.allPresets.orderedEntries) {
                        TabButton(
                            modifier = Modifier.fillMaxWidth(),
                            checked = uiState.selectedPresetUuid == uuid,
                            onClick = {
                                screenModel.selectPreset(uuid)
                            }
                        ) {
                            Text(preset.name)
                        }
                    }
                }
            }
        }
    }
}