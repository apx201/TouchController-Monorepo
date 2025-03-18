package top.fifthlight.touchcontroller.common.control

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import top.fifthlight.combine.data.TextFactory
import top.fifthlight.data.IntOffset
import top.fifthlight.data.IntSize
import top.fifthlight.touchcontroller.assets.Texts
import top.fifthlight.touchcontroller.assets.TextureSet
import top.fifthlight.touchcontroller.common.ext.fastRandomUuid
import top.fifthlight.touchcontroller.common.layout.Align
import top.fifthlight.touchcontroller.common.layout.Context
import top.fifthlight.touchcontroller.common.layout.DPad
import kotlin.math.round
import kotlin.uuid.Uuid

@Serializable
enum class DPadExtraButton {
    @SerialName("none")
    NONE,

    @SerialName("sneak_double_click")
    SNEAK_DOUBLE_CLICK,

    @SerialName("sneak_single_click")
    SNEAK_SINGLE_CLICK,

    @SerialName("sneak_hold")
    SNEAK_HOLD,

    @SerialName("dismount_double_click")
    DISMOUNT_DOUBLE_CLICK,

    @SerialName("dismount_single_click")
    DISMOUNT_SINGLE_CLICK,

    @SerialName("jump")
    JUMP,

    @SerialName("jump_without_locking")
    JUMP_WITHOUT_LOCKING,

    @SerialName("flying")
    FLYING,
}

@Serializable
@SerialName("dpad")
data class DPad(
    val textureSet: TextureSet.TextureSetKey = TextureSet.TextureSetKey.CLASSIC,
    val size: Float = 2f,
    val padding: Int = if (textureSet == TextureSet.TextureSetKey.CLASSIC || textureSet == TextureSet.TextureSetKey.CLASSIC_EXTENSION) 4 else -1,
    val extraButton: DPadExtraButton = if (textureSet == TextureSet.TextureSetKey.CLASSIC || textureSet == TextureSet.TextureSetKey.CLASSIC_EXTENSION) DPadExtraButton.SNEAK_DOUBLE_CLICK else DPadExtraButton.SNEAK_SINGLE_CLICK,
    val extraButtonSize: Int = 22,
    val idForward: Uuid = fastRandomUuid(),
    val idBackward: Uuid = fastRandomUuid(),
    val idLeft: Uuid = fastRandomUuid(),
    val idRight: Uuid = fastRandomUuid(),
    val idLeftForward: Uuid = fastRandomUuid(),
    val idRightForward: Uuid = fastRandomUuid(),
    val idLeftBackward: Uuid = fastRandomUuid(),
    val idRightBackward: Uuid = fastRandomUuid(),
    val idExtraButton: Uuid = fastRandomUuid(),
    override val id: Uuid = fastRandomUuid(),
    override val name: Name = Name.Translatable(Texts.WIDGET_DPAD_NAME),
    override val align: Align = Align.LEFT_BOTTOM,
    override val offset: IntOffset = IntOffset.ZERO,
    override val opacity: Float = 1f,
    override val lockMoving: Boolean = false,
) : ControllerWidget() {
    companion object : KoinComponent {
        private val textFactory: TextFactory by inject()

        @Suppress("UNCHECKED_CAST")
        private val _properties = properties + persistentListOf<Property<DPad, *>>(
            EnumProperty(
                getValue = { it.extraButton },
                setValue = { config, value -> config.copy(extraButton = value) },
                name = textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_NAME),
                items = persistentListOf(
                    DPadExtraButton.NONE to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_NONE),
                    DPadExtraButton.SNEAK_DOUBLE_CLICK to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_SNEAK_DOUBLE_CLICK),
                    DPadExtraButton.SNEAK_SINGLE_CLICK to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_SNEAK_SINGLE_CLICK),
                    DPadExtraButton.SNEAK_HOLD to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_SNEAK_HOLD),
                    DPadExtraButton.DISMOUNT_SINGLE_CLICK to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_DISMOUNT_DOUBLE_CLICK),
                    DPadExtraButton.DISMOUNT_DOUBLE_CLICK to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_DISMOUNT_SINGLE_CLICK),
                    DPadExtraButton.JUMP to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_JUMP),
                    DPadExtraButton.JUMP_WITHOUT_LOCKING to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_JUMP_WITHOUT_LOCKING),
                    DPadExtraButton.FLYING to textFactory.of(Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_FUNCTION_FLYING),
                ),
            ),
            TextureSetProperty(
                textFactory = textFactory,
                getValue = { it.textureSet },
                setValue = { config, value -> config.copy(textureSet = value) },
                name = textFactory.of(Texts.WIDGET_DPAD_PROPERTY_TEXTURE_SET),
            ),
            FloatProperty(
                getValue = { it.size },
                setValue = { config, value -> config.copy(size = value) },
                range = .5f..4f,
                messageFormatter = {
                    textFactory.format(
                        Texts.WIDGET_DPAD_PROPERTY_SIZE,
                        round(it * 100f).toString()
                    )
                },
            ),
            IntProperty(
                getValue = { it.padding },
                setValue = { config, value -> config.copy(padding = value) },
                range = -1..16,
                messageFormatter = { textFactory.format(Texts.WIDGET_DPAD_PROPERTY_PADDING, it) }
            ),
            IntProperty(
                getValue = { it.extraButtonSize },
                setValue = { config, value -> config.copy(extraButtonSize = value) },
                range = 12..22,
                messageFormatter = {
                    textFactory.format(
                        Texts.WIDGET_DPAD_PROPERTY_EXTRA_BUTTON_SIZE,
                        it
                    )
                }
            ),
        ) as PersistentList<Property<ControllerWidget, *>>
    }

    override val properties
        get() = _properties

    val classic = textureSet == TextureSet.TextureSetKey.CLASSIC || textureSet == TextureSet.TextureSetKey.CLASSIC_EXTENSION

    fun buttonSize() = IntSize(((textureSet.textureSet.up.size.width + padding) * size).toInt())
    fun buttonDisplaySize() = IntSize((textureSet.textureSet.up.size.width * size).toInt())
    fun smallButtonDisplaySize() = IntSize((textureSet.textureSet.upLeft.size.width * size).toInt())
    fun extraButtonDisplaySize() = IntSize((extraButtonSize * size).toInt())

    override fun size(): IntSize = buttonSize() * 3

    override fun layout(context: Context) = context.DPad(this@DPad)

    override fun cloneBase(
        id: Uuid,
        name: Name,
        align: Align,
        offset: IntOffset,
        opacity: Float,
        lockMoving: Boolean
    ) = copy(
        id = id,
        name = name,
        align = align,
        offset = offset,
        opacity = opacity,
        lockMoving = lockMoving,
    )

    override fun newId(): ControllerWidget = copy(
        idForward = fastRandomUuid(),
        idBackward = fastRandomUuid(),
        idLeft = fastRandomUuid(),
        idRight = fastRandomUuid(),
        idLeftForward = fastRandomUuid(),
        idRightForward = fastRandomUuid(),
        idLeftBackward = fastRandomUuid(),
        idRightBackward = fastRandomUuid(),
        idExtraButton = fastRandomUuid(),
        id = fastRandomUuid(),
    )
}
