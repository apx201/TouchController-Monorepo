package top.fifthlight.touchcontroller.common.control

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SneakButtonTexture {
    @SerialName("classic")
    CLASSIC,

    @SerialName("new")
    NEW,

    @SerialName("new_dpad")
    NEW_DPAD,

    @SerialName("dismount")
    DISMOUNT,

    @SerialName("dismount_dpad")
    DISMOUNT_DPAD,
}

@Serializable
enum class SneakButtonTrigger {
    @SerialName("double_click_lock")
    DOUBLE_CLICK_LOCK,

    @SerialName("single_click_lock")
    SINGLE_CLICK_LOCK,

    @SerialName("hold")
    HOLD,

    @SerialName("single_click_trigger")
    SINGLE_CLICK_TRIGGER,

    @SerialName("double_click_trigger")
    DOUBLE_CLICK_TRIGGER,
}
