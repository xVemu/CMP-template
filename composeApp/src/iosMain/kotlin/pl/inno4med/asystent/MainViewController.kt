package pl.inno4med.asystent

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.ksp.generated.startKoin
import pl.inno4med.asystent.di.MainKoinApplication

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    MainKoinApplication.startKoin()
}
