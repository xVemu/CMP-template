package pl.inno4med.asystent

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.AppUpdateResult
import com.google.android.play.core.ktx.clientVersionStalenessDays
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.requestAppUpdateInfo
import com.google.android.play.core.ktx.requestUpdateFlow
import com.mmk.kmpnotifier.permission.permissionUtil
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    init {
        // Disables exit on crash TODO
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("UncaughtException", "Thread: $t, Exception: $e")
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: Activity? = null // needed for in-app review
            private set
    }

    val permissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        permissionUtil.askNotificationPermission()

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

    override fun onResume() {
        super.onResume()
        instance = this
    }

    override fun onPause() {
        super.onPause()
        instance = null
    }
}
