package stanley.eldlichjcc

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Eldlich Jcc Platform",
    ) {
        App()
    }
}