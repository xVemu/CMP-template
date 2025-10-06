package pl.inno4med.asystent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmk.kmpnotifier.permission.permissionUtil

class MainActivity : ComponentActivity() {

    init {
        // Disables exit on crash TODO
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("UncaughtException", "Thread: $t, Exception: $e")
        }
    }

    val permissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        permissionUtil.askNotificationPermission()

        setContent {
            App()
        }
    }
}
