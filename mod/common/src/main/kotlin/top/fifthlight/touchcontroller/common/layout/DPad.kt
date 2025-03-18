package top.fifthlight.touchcontroller.common.layout

import top.fifthlight.combine.paint.Color
import top.fifthlight.touchcontroller.assets.TextureSet
import top.fifthlight.touchcontroller.assets.Textures
import top.fifthlight.touchcontroller.common.control.*
import top.fifthlight.touchcontroller.common.gal.DefaultKeyBindingType
import top.fifthlight.touchcontroller.common.state.PointerState

fun Context.DPad(config: DPad) {
    val buttonSize = config.buttonSize()
    val buttonDisplaySize = config.buttonDisplaySize()
    val smallDisplaySize = config.smallButtonDisplaySize()
    val extraButtonDisplaySize = config.extraButtonDisplaySize()
    val offset = (buttonDisplaySize - smallDisplaySize) / 2

    val forward = withRect(
        x = buttonSize.width,
        y = 0,
        width = buttonSize.width,
        height = buttonSize.height
    ) {
        SwipeButton(id = config.idForward) { clicked ->
            withAlign(
                align = Align.CENTER_CENTER,
                size = buttonDisplaySize
            ) {
                when (Pair(config.classic, clicked)) {
                    Pair(true, false) -> Texture(texture = config.textureSet.textureSet.up)
                    Pair(true, true) -> Texture(texture = config.textureSet.textureSet.up, tint = Color(0xFFAAAAAAu))
                    Pair(false, false) -> Texture(texture = config.textureSet.textureSet.up)
                    Pair(false, true) -> Texture(texture = config.textureSet.textureSet.upActive)
                }
            }
        }.clicked
    }

    val backward = withRect(
        x = buttonSize.width,
        y = buttonSize.height * 2,
        width = buttonSize.width,
        height = buttonSize.height
    ) {
        SwipeButton(id = config.idBackward) { clicked ->
            withAlign(
                align = Align.CENTER_CENTER,
                size = buttonDisplaySize
            ) {
                when (Pair(config.classic, clicked)) {
                    Pair(true, false) -> Texture(texture = config.textureSet.textureSet.down)
                    Pair(true, true) -> Texture(texture = config.textureSet.textureSet.down, tint = Color(0xFFAAAAAAu))
                    Pair(false, false) -> Texture(texture = config.textureSet.textureSet.down)
                    Pair(false, true) -> Texture(texture = config.textureSet.textureSet.downActive)
                }
            }
        }.clicked
    }

    val left = withRect(
        x = 0,
        y = buttonSize.height,
        width = buttonSize.width,
        height = buttonSize.height
    ) {
        SwipeButton(id = config.idLeft) { clicked ->
            withAlign(
                align = Align.CENTER_CENTER,
                size = buttonDisplaySize
            ) {
                when (Pair(config.classic, clicked)) {
                    Pair(true, false) -> Texture(texture = config.textureSet.textureSet.left)
                    Pair(true, true) -> Texture(texture = config.textureSet.textureSet.left, tint = Color(0xFFAAAAAAu))
                    Pair(false, false) -> Texture(texture = config.textureSet.textureSet.left)
                    Pair(false, true) -> Texture(texture = config.textureSet.textureSet.leftActive)
                }
            }
        }.clicked
    }

    val right = withRect(
        x = buttonSize.width * 2,
        y = buttonSize.height,
        width = buttonSize.width,
        height = buttonSize.height
    ) {
        SwipeButton(id = config.idRight) { clicked ->
            withAlign(
                align = Align.CENTER_CENTER,
                size = buttonDisplaySize
            ) {
                when (Pair(config.classic, clicked)) {
                    Pair(true, false) -> Texture(texture = config.textureSet.textureSet.right)
                    Pair(true, true) -> Texture(texture = config.textureSet.textureSet.right, tint = Color(0xFFAAAAAAu))
                    Pair(false, false) -> Texture(texture = config.textureSet.textureSet.right)
                    Pair(false, true) -> Texture(texture = config.textureSet.textureSet.rightActive)
                }
            }
        }.clicked
    }

    val showLeftForward = forward || left || status.dpadLeftForwardShown
    val showRightForward = forward || right || status.dpadRightForwardShown
    val showLeftBackward = !config.classic && (backward || left || status.dpadLeftBackwardShown)
    val showRightBackward = !config.classic && (backward || right || status.dpadRightBackwardShown)

    val leftForward = if (showLeftForward) {
        withRect(
            x = 0,
            y = 0,
            width = buttonSize.width,
            height = buttonSize.height
        ) {
            SwipeButton(id = config.idLeftForward) { clicked ->
                withAlign(
                    align = Align.RIGHT_BOTTOM,
                    size = smallDisplaySize,
                    offset = offset,
                ) {
                    when (Pair(config.classic, clicked)) {
                        Pair(true, false) -> Texture(texture = config.textureSet.textureSet.upLeft)
                        Pair(true, true) -> Texture(texture = config.textureSet.textureSet.upLeft, tint = Color(0xFFAAAAAAu))
                        Pair(false, false) -> Texture(texture = config.textureSet.textureSet.upLeft)
                        Pair(false, true) -> Texture(texture = config.textureSet.textureSet.upLeftActive)
                    }
                }
            }.clicked
        }
    } else {
        false
    }

    val rightForward = if (showRightForward) {
        withRect(
            x = buttonSize.width * 2,
            y = 0,
            width = buttonSize.width,
            height = buttonSize.height
        ) {
            SwipeButton(id = config.idRightForward) { clicked ->
                withAlign(
                    align = Align.LEFT_BOTTOM,
                    size = smallDisplaySize,
                    offset = offset,
                ) {
                    when (Pair(config.classic, clicked)) {
                        Pair(true, false) -> Texture(texture = config.textureSet.textureSet.upRight)
                        Pair(true, true) -> Texture(texture = config.textureSet.textureSet.upRight, tint = Color(0xFFAAAAAAu))
                        Pair(false, false) -> Texture(texture = config.textureSet.textureSet.upRight)
                        Pair(false, true) -> Texture(texture = config.textureSet.textureSet.upRightActive)
                    }
                }
            }.clicked
        }
    } else {
        false
    }

    val leftBackward = if (showLeftBackward) {
        withRect(
            x = 0,
            y = buttonSize.height * 2,
            width = buttonSize.width,
            height = buttonSize.height
        ) {
            SwipeButton(id = config.idLeftBackward) { clicked ->
                withAlign(
                    align = Align.RIGHT_TOP,
                    size = smallDisplaySize,
                    offset = offset,
                ) {
                    if (!clicked) {
                        Texture(texture = config.textureSet.textureSet.upRight)
                    } else {
                        Texture(texture = config.textureSet.textureSet.upRightActive)
                    }
                }
            }.clicked
        }
    } else {
        false
    }

    val rightBackward = if (showRightBackward) {
        withRect(
            x = buttonSize.width * 2,
            y = buttonSize.width * 2,
            width = buttonSize.width,
            height = buttonSize.height
        ) {
            SwipeButton(id = config.idRightBackward) { clicked ->
                withAlign(
                    align = Align.LEFT_TOP,
                    size = smallDisplaySize,
                    offset = offset,
                ) {
                    if (!clicked) {
                        Texture(texture = config.textureSet.textureSet.downRight)
                    } else {
                        Texture(texture = config.textureSet.textureSet.downRightActive)
                    }
                }
            }.clicked
        }
    } else {
        false
    }

    status.dpadLeftForwardShown = left || forward || leftForward
    status.dpadRightForwardShown = right || forward || rightForward
    status.dpadLeftBackwardShown = !config.classic && (left || backward || leftBackward)
    status.dpadRightBackwardShown = !config.classic && (right || backward || rightBackward)

    when (Pair(forward || leftForward || rightForward, backward || leftBackward || rightBackward)) {
        Pair(true, false) -> result.forward = 1f
        Pair(false, true) -> result.forward = -1f
    }

    when (Pair(left || leftForward || leftBackward, right || rightForward || rightBackward)) {
        Pair(true, false) -> result.left = 1f
        Pair(false, true) -> result.left = -1f
    }

    when {
        forward -> DPadDirection.UP
        backward -> DPadDirection.DOWN
        left -> DPadDirection.LEFT
        right -> DPadDirection.RIGHT
        else -> null
    }?.let { status.lastDpadDirection = it }

    withRect(
        x = buttonSize.width,
        y = buttonSize.height,
        width = buttonSize.width,
        height = buttonSize.height
    ) {
        val sneakButtonTexture = if (config.classic) {
            SneakButtonTexture.CLASSIC
        } else if (config.padding < 0 && config.extraButtonDisplaySize() == buttonSize) {
            SneakButtonTexture.NEW_DPAD
        } else {
            SneakButtonTexture.NEW
        }
        when (config.extraButton) {
            DPadExtraButton.NONE -> {}
            DPadExtraButton.SNEAK_DOUBLE_CLICK -> RawSneakButton(
                id = config.idExtraButton,
                texture = sneakButtonTexture,
                trigger = SneakButtonTrigger.DOUBLE_CLICK_LOCK,
                size = extraButtonDisplaySize
            )

            DPadExtraButton.SNEAK_SINGLE_CLICK -> RawSneakButton(
                id = config.idExtraButton,
                texture = sneakButtonTexture,
                trigger = SneakButtonTrigger.SINGLE_CLICK_LOCK,
                size = extraButtonDisplaySize
            )

            DPadExtraButton.SNEAK_HOLD -> RawSneakButton(
                id = config.idExtraButton,
                texture = sneakButtonTexture,
                trigger = SneakButtonTrigger.HOLD,
                size = extraButtonDisplaySize
            )

            DPadExtraButton.DISMOUNT_SINGLE_CLICK -> RawSneakButton(
                id = config.idExtraButton,
                texture = if (config.classic) {
                    SneakButtonTexture.CLASSIC
                } else {
                    SneakButtonTexture.DISMOUNT_DPAD
                },
                trigger = SneakButtonTrigger.SINGLE_CLICK_TRIGGER,
                size = extraButtonDisplaySize
            )

            DPadExtraButton.DISMOUNT_DOUBLE_CLICK -> RawSneakButton(
                id = config.idExtraButton,
                texture = if (config.classic) {
                    SneakButtonTexture.CLASSIC
                } else {
                    SneakButtonTexture.DISMOUNT_DPAD
                },
                trigger = SneakButtonTrigger.DOUBLE_CLICK_TRIGGER,
                size = extraButtonDisplaySize
            )

            DPadExtraButton.JUMP, DPadExtraButton.JUMP_WITHOUT_LOCKING, DPadExtraButton.FLYING -> {
                var hasPointer = false
                for (pointer in getPointersInRect(size)) {
                    val state = (pointer.state as? PointerState.SwipeButton) ?: continue
                    if (state.id == config.idForward || state.id == config.idBackward || state.id == config.idLeft || state.id == config.idRight) {
                        hasPointer = true
                    }
                }
                val (_, clicked, _) = DPadJumpButton(
                    id = config.idExtraButton,
                    size = extraButtonDisplaySize,
                    texture = if (!config.classic) {
                        JumpButtonTexture.NEW
                    } else {
                        when (config.extraButton) {
                            DPadExtraButton.JUMP, DPadExtraButton.JUMP_WITHOUT_LOCKING -> JumpButtonTexture.CLASSIC
                            DPadExtraButton.FLYING -> JumpButtonTexture.CLASSIC_FLYING
                            else -> error("Unreachable")
                        }
                    }
                )
                val jumpKeyBinding = keyBindingHandler.getState(DefaultKeyBindingType.JUMP)
                if (clicked) {
                    if (config.extraButton == DPadExtraButton.JUMP_WITHOUT_LOCKING || !hasPointer) {
                        jumpKeyBinding.clicked = true
                    } else {
                        if (!status.dpadJumping) {
                            jumpKeyBinding.clicked = true
                            status.dpadJumping = true
                        }
                    }
                    if (hasPointer) {
                        when (status.lastDpadDirection) {
                            DPadDirection.UP -> result.forward = 1f
                            DPadDirection.DOWN -> result.forward = -1f
                            DPadDirection.LEFT -> result.left = 1f
                            DPadDirection.RIGHT -> result.left = -1f
                            null -> {}
                        }
                    }
                } else {
                    status.dpadJumping = false
                }
            }
        }
    }
}