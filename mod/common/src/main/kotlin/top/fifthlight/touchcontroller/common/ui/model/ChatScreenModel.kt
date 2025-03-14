package top.fifthlight.touchcontroller.common.ui.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import org.koin.core.component.inject
import top.fifthlight.touchcontroller.common.gal.ChatMessageProvider
import top.fifthlight.touchcontroller.common.ui.state.ChatScreenState

class ChatScreenModel : TouchControllerScreenModel() {
    private val chatMessageProvider: ChatMessageProvider by inject()
    private val _uiState = MutableStateFlow(ChatScreenState())
    val uiState = _uiState.asStateFlow()

    fun updateText(newText: String) {
        _uiState.getAndUpdate { it.copy(text = newText) }
    }

    fun sendText() {
        chatMessageProvider.sendMessage(uiState.value.text)
        updateText("")
    }
}