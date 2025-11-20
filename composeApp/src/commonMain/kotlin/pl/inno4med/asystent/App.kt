package pl.inno4med.asystent

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import coil3.ColorImage
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.architect.kmpessentials.permissions.KmpPermissionsManager
import com.architect.kmpessentials.permissions.Permission
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import pl.inno4med.asystent.di.review
import pl.inno4med.asystent.navigation.NavigationHost
import pl.inno4med.asystent.navigation.bottomItems
import pl.inno4med.asystent.theme.AppTheme
import pl.inno4med.components.LocalNavController
import pl.inno4med.components.PrimaryScaffold
import kotlin.time.ExperimentalTime

@OptIn(KoinExperimentalAPI::class, ExperimentalTime::class)
@Composable
fun App() {
//    KoinMultiplatformApplication(config = DefaultKoinConfiguration) { WAIT https://github.com/InsertKoinIO/koin/issues/2078 https://github.com/InsertKoinIO/koin/issues/2203

    val dataStore = koinInject<DataStore<Preferences>>()

    LaunchedEffect(Unit) {
        Logger.setTag("Application")
        KmpPermissionsManager.requestPermission(Permission.PushNotifications) {}
        review(dataStore)
    }

    AppTheme {
        val color = MaterialTheme.colorScheme.onSurface.copy(.1F)

        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .placeholder(ColorImage(color.toArgb()))
                .error(ColorImage(color.toArgb()))
                .build()
        }

        val navController = rememberNavController()

        CompositionLocalProvider(LocalNavController provides navController) {
            PrimaryScaffold(navController, bottomItems) {
                NavigationHost(navController)
            }
        }
    }
}
