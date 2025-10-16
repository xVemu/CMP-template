package pl.inno4med.asystent

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import pl.inno4med.asystent.di.DefaultKoinConfiguration

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin(DefaultKoinConfiguration)
}
