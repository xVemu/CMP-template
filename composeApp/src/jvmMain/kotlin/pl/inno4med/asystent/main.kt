package pl.inno4med.asystent

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

fun main() = application {
    NotifierManager.initialize(NotificationPlatformConfiguration.Desktop())

    Window(
        onCloseRequest = ::exitApplication,
        title = "Asystent",
    ) {
        App()
    }
}
