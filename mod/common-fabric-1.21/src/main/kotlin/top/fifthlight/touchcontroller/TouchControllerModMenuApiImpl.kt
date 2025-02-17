package top.fifthlight.touchcontroller

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.minecraft.client.gui.screen.Screen
import top.fifthlight.touchcontroller.ui.screen.getConfigScreen

class TouchControllerModMenuApiImpl : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<Screen> =
        ConfigScreenFactory<Screen> { parent -> getConfigScreen(parent) as Screen }
}