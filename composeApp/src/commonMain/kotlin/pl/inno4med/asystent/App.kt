package pl.inno4med.asystent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import co.touchlab.kermit.Logger
import com.architect.kmpessentials.permissions.KmpPermissionsManager
import com.architect.kmpessentials.permissions.Permission
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import pl.inno4med.asystent.di.DefaultKoinConfiguration
import pl.inno4med.asystent.di.review

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinMultiplatformApplication(config = DefaultKoinConfiguration) {

        val dataStore = koinInject<DataStore<Preferences>>()

        LaunchedEffect(Unit) {
            Logger.setTag("Application")
            KmpPermissionsManager.requestPermission(Permission.PushNotifications) {}
            review(dataStore)
        }

        MaterialTheme {
            Box(Modifier.safeDrawingPadding()) {
                NavigationHost()
            }
        }
    }
}
