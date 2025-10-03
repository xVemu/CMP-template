package pl.inno4med.asystent

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import multiplatform.network.cmptoast.ToastHost

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Asystent",
    ) {
        App()

        ToastHost()
    }
}
