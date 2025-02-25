package top.fifthlight.touchcontroller.config.preset.builtin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.fifthlight.touchcontroller.assets.TextureSet

@Serializable
data class BuiltinPresetKey(
    val textureSet: TextureSet.TextureSetKey = TextureSet.TextureSetKey.CLASSIC,
    val controlStyle: ControlStyle = ControlStyle.CLICK_TO_INTERACT,
    val moveMethod: MoveMethod = MoveMethod.Dpad(),
    val sprintButtonLocation: SprintButtonLocation = SprintButtonLocation.NONE,
) {
    @Serializable
    enum class ControlStyle {
        @SerialName("touch_gesture")
        CLICK_TO_INTERACT,

        @SerialName("split_controls_and_touch_gesture")
        AIM_BY_CROSSHAIR,

        @SerialName("split_controls_and_buttons")
        AIM_BY_CROSSHAIR_AND_USE_BUTTON_TO_INTERACT
    }

    @Serializable
    enum class SprintButtonLocation {
        @SerialName("none")
        NONE,

        @SerialName("right_top")
        RIGHT_TOP,

        @SerialName("right")
        RIGHT,
    }

    @Serializable
    sealed class MoveMethod {
        @Serializable
        @SerialName("dpad")
        data class Dpad(
            val swapJumpAndSneak: Boolean = false,
        ) : MoveMethod()

        @Serializable
        @SerialName("joystick")
        data class Joystick(
            val triggerSprint: Boolean = false,
        ) : MoveMethod()
    }

    companion object {
        val DEFAULT = BuiltinPresetKey()
    }
}