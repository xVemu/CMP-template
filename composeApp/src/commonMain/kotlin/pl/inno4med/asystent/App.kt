package pl.inno4med.asystent

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import com.architect.kmpessentials.permissions.KmpPermissionsManager
import com.architect.kmpessentials.permissions.Permission
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import pl.inno4med.asystent.di.DefaultKoinConfiguration
import pl.inno4med.asystent.di.review
import pl.inno4med.components.PrimaryScaffold

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
            val navController = rememberNavController()

            CompositionLocalProvider(LocalNavController provides navController) {
                PrimaryScaffold(navController, bottomItems) {
                    NavigationHost(navController)
                }
            }
        }
    }
}
