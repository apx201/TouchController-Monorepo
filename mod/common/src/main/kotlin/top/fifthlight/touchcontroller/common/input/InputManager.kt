package top.fifthlight.touchcontroller.common.input

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import top.fifthlight.combine.input.input.InputHandler
import top.fifthlight.combine.input.input.TextInputState
import top.fifthlight.data.IntRect
import top.fifthlight.touchcontroller.common.event.RenderEvents
import top.fifthlight.touchcontroller.common.gal.GameDispatcher
import top.fifthlight.touchcontroller.common.gal.WindowHandle
import top.fifthlight.touchcontroller.common.platform.PlatformProvider
import top.fifthlight.touchcontroller.proxy.message.InputCursorMessage
import top.fifthlight.touchcontroller.proxy.message.InputStatusMessage
import top.fifthlight.touchcontroller.proxy.message.KeyboardShowMessage
import top.fifthlight.touchcontroller.proxy.message.input.TextInputState as ProxyTextInputState
import top.fifthlight.touchcontroller.proxy.message.input.TextRange as ProxyTextRange

object InputManager : KoinComponent, InputHandler {
    private val platformProvider: PlatformProvider by inject()
    private var inputState: TextInputState? = null
    private var cursorRect: IntRect? = null
    private val windowHandle: WindowHandle by inject()
    private val _events = MutableSharedFlow<TextInputState>()
    override val events = _events.asSharedFlow()
    private val gameDispatcher: GameDispatcher by inject()
    private val scope = CoroutineScope(SupervisorJob() + gameDispatcher)

    fun updateNativeState(textInputState: TextInputState) {
        inputState = textInputState
        scope.launch {
            _events.emit(textInputState)
        }
    }

    override fun updateInputState(textInputState: TextInputState?, cursorRect: IntRect?) {
        val inputStateUpdated = inputState != textInputState
        val cursorRectUpdated = cursorRect != this.cursorRect
        this.inputState = textInputState
        this.cursorRect = cursorRect
        if (RenderEvents.platformCapabilities.textStatus) {
            platformProvider.platform?.let { platform ->
                if (inputStateUpdated) {
                    if (RenderEvents.platformCapabilities.textStatus) {
                        platform.sendEvent(InputStatusMessage(textInputState?.let {
                            ProxyTextInputState(
                                text = textInputState.text,
                                composition = ProxyTextRange(
                                    start = textInputState.composition.start,
                                    length = textInputState.composition.length,
                                ),
                                selection = ProxyTextRange(
                                    start = textInputState.selection.start,
                                    length = textInputState.selection.length,
                                ),
                                selectionLeft = textInputState.selectionLeft,
                            )
                        }))
                    }
                }
                if (cursorRectUpdated) {
                    cursorRect?.let { rect ->
                        platform.sendEvent(
                            InputCursorMessage(
                                InputCursorMessage.CursorRect(
                                    left = rect.offset.left.toFloat() / windowHandle.scaledSize.width.toFloat(),
                                    top = rect.offset.top.toFloat() / windowHandle.scaledSize.height.toFloat(),
                                    width = rect.size.width.toFloat() / windowHandle.scaledSize.width.toFloat(),
                                    height = rect.size.height.toFloat() / windowHandle.scaledSize.height.toFloat(),
                                )
                            )
                        )
                    } ?: {
                        platform.sendEvent(InputCursorMessage(null))
                    }
                }
            }
        }
    }

    override fun tryShowKeyboard() {
        platformProvider.platform?.let { platform ->
            if (RenderEvents.platformCapabilities.keyboardShow) {
                platform.sendEvent(KeyboardShowMessage(true))
            }
        }
    }

    override fun tryHideKeyboard() {
        platformProvider.platform?.let { platform ->
            if (RenderEvents.platformCapabilities.keyboardShow) {
                platform.sendEvent(KeyboardShowMessage(false))
            }
        }
    }
}
