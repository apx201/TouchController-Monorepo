package top.fifthlight.touchcontroller.common.ui.state

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import top.fifthlight.touchcontroller.assets.TextureSet
import top.fifthlight.touchcontroller.common.control.AscendButton
import top.fifthlight.touchcontroller.common.control.BoatButton
import top.fifthlight.touchcontroller.common.control.ChatButton
import top.fifthlight.touchcontroller.common.control.ControllerWidget
import top.fifthlight.touchcontroller.common.control.CustomWidget
import top.fifthlight.touchcontroller.common.control.DPad
import top.fifthlight.touchcontroller.common.control.DescendButton
import top.fifthlight.touchcontroller.common.control.ForwardButton
import top.fifthlight.touchcontroller.common.control.HideHudButton
import top.fifthlight.touchcontroller.common.control.InventoryButton
import top.fifthlight.touchcontroller.common.control.Joystick
import top.fifthlight.touchcontroller.common.control.JumpButton
import top.fifthlight.touchcontroller.common.control.PanoramaButton
import top.fifthlight.touchcontroller.common.control.PauseButton
import top.fifthlight.touchcontroller.common.control.PerspectiveSwitchButton
import top.fifthlight.touchcontroller.common.control.PlayerListButton
import top.fifthlight.touchcontroller.common.control.ScreenshotButton
import top.fifthlight.touchcontroller.common.control.SneakButton
import top.fifthlight.touchcontroller.common.control.SprintButton
import top.fifthlight.touchcontroller.common.control.UseButton

data class WidgetsTabState(
    val listState: ListState = ListState.Builtin,
    val dialogState: DialogState = DialogState.Empty,
    val newWidgetParams: NewWidgetParams = NewWidgetParams(),
) {
    data class NewWidgetParams(
        val opacity: Float = .6f,
        val textureSet: TextureSet.TextureSetKey = TextureSet.TextureSetKey.CLASSIC,
    )

    sealed class DialogState {
        data object Empty : DialogState()

        data class ChangeNewWidgetParams(
            val opacity: Float = .6f,
            val textureSet: TextureSet.TextureSetKey = TextureSet.TextureSetKey.CLASSIC,
        ) : DialogState() {
            constructor(params: NewWidgetParams) : this(opacity = params.opacity, textureSet = params.textureSet)

            fun toParams() = NewWidgetParams(
                opacity = opacity,
                textureSet = textureSet,
            )
        }
    }

    sealed class ListState {
        open val heroes: PersistentList<ControllerWidget>? = null
        open val widgets: PersistentList<ControllerWidget>? = null

        data object Builtin : ListState() {
            override val heroes = persistentListOf<ControllerWidget>(
                DPad(),
                Joystick()
            )

            override val widgets = persistentListOf<ControllerWidget>(
                AscendButton(),
                DescendButton(),
                BoatButton(),
                ChatButton(),
                DescendButton(),
                ForwardButton(),
                HideHudButton(),
                InventoryButton(),
                JumpButton(),
                PanoramaButton(),
                PauseButton(),
                PerspectiveSwitchButton(),
                PlayerListButton(),
                ScreenshotButton(),
                SneakButton(),
                SprintButton(),
                UseButton(),
                CustomWidget(),
            )
        }

        sealed class Custom : ListState() {
            data object Loading : Custom()

            data class Loaded(
                override val widgets: PersistentList<ControllerWidget>? = null,
            ) : Custom()
        }
    }
}