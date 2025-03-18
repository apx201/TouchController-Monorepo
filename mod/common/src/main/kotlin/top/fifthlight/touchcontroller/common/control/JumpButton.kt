package top.fifthlight.touchcontroller.common.control

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class JumpButtonTexture {
    @SerialName("classic")
    CLASSIC,

    @SerialName("classic_flying")
    CLASSIC_FLYING,

    @SerialName("new")
    NEW,

    @SerialName("new_horse")
    NEW_HORSE,
}
