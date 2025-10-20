package pl.inno4med.asystent

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.core.annotation.KoinInternalApi
import org.koin.ksp.generated.startKoin
import pl.inno4med.asystent.di.MainKoinApplication

@OptIn(KoinInternalApi::class)
fun main() = application {
    NotifierManager.initialize(NotificationPlatformConfiguration.Desktop())

    MainKoinApplication.startKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Asystent",
    ) {
        App()
    }
}
