package pl.inno4med.asystent

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.startKoin
import pl.inno4med.asystent.di.DefaultKoinConfiguration

@OptIn(KoinInternalApi::class)
fun main() = application {
    NotifierManager.initialize(NotificationPlatformConfiguration.Desktop())

    startKoin(DefaultKoinConfiguration)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Asystent",
    ) {
        App()
    }
}
