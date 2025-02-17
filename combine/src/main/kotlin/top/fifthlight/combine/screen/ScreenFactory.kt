package top.fifthlight.combine.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import top.fifthlight.combine.data.Text

val LocalScreenFactory = staticCompositionLocalOf<ScreenFactory> { error("No ScreenFactory in context") }

interface ScreenFactory {
    fun openScreen(
        title: Text,
        content: @Composable () -> Unit,
    )

    fun getScreen(
        parent: Any?,
        title: Text,
        content: @Composable () -> Unit,
    ): Any
}
