package pl.inno4med.asystent

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.architect.kmpessentials.KmpAndroid
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.AppUpdateResult
import com.google.android.play.core.ktx.clientVersionStalenessDays
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.requestAppUpdateInfo
import com.google.android.play.core.ktx.requestUpdateFlow
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    init {
        // Disables exit on crash TODO
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("UncaughtException", "Thread: $t, Exception: $e")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Allows to change navbar color.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        KmpAndroid.initializeApp(this)

        setContent {
            App()
        }

        lifecycleScope.launch {
            updateApp()
        }
    }

    private suspend fun updateApp() {
        val manager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfo = manager.requestAppUpdateInfo()
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isFlexibleUpdateAllowed && (appUpdateInfo.clientVersionStalenessDays
                ?: -1) < 7
        ) return

        manager.startUpdateFlowForResult(
            appUpdateInfo,
            this,
            AppUpdateOptions.defaultOptions(AppUpdateType.FLEXIBLE),
            0
        )
        manager.requestUpdateFlow().collect { appUpdateResult ->
            if (appUpdateResult is AppUpdateResult.Downloaded) appUpdateResult.completeUpdate()
        }
    }
}
