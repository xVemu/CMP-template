package pl.inno4med.asystent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImage
import com.mmk.kmpnotifier.notification.NotificationImage
import com.mmk.kmpnotifier.notification.Notifier
import com.mmk.kmpnotifier.notification.NotifierManager
import kotlinx.coroutines.launch
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import pl.inno4med.asystent.di.DefaultKoinConfiguration
import pl.inno4med.asystent.utils.Result
import pl.inno4med.asystent.utils.makeToast
import pl.inno4med.components.CustomError
import kotlin.random.Random

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinMultiplatformApplication(config = DefaultKoinConfiguration) {

        LaunchedEffect(Unit) {
            Logger.setTag("Application")
        }

        MaterialTheme {
            var showContent by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var result by remember { mutableStateOf<Result<TodoEntity>>(Result.Loading) }

                val scope = rememberCoroutineScope()

                Button(onClick = {
                    showContent = !showContent
                    result = if (result.isLoading)
                        Result.Success(TodoEntity(1, "Title", "Content"))
                    else Result.Loading

                    makeToast("Toast")
                    Logger.d { "Button clicked, showContent: $showContent" }
                    scope.launch {
                        sendNotification()
                    }
                }) {
                    Text("Click me!")
                }

                result.switchPlaceholder(null, TodoEntity(0, "arsars", "arsars")) {
                    Row/*(Modifier.background(Color.Red))*/ {
                        AsyncImage("https://picsum.photos/200/300", null)
                        Spacer(Modifier.width(80.dp))
                        Text("Title: ${it.title}", style = MaterialTheme.typography.titleLarge)
                    }
                }

                AnimatedVisibility(showContent) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
//                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        CustomError { }
                    }
                }
            }
        }
    }
}

suspend fun sendNotification() {
    Logger.d { "Token: ${NotifierManager.getPushNotifier().getToken()}" }
    val notifier = NotifierManager.getLocalNotifier()
    notifier.notify {
        id = Random.nextInt(0, Int.MAX_VALUE)
        title = "Title from KMPNotifier"
        body = "Body message from KMPNotifier"
        payloadData = mapOf(
            Notifier.KEY_URL to "https://github.com/mirzemehdi/KMPNotifier/",
            "extraKey" to "randomValue"
        )
        image =
            NotificationImage.Url("https://github.com/user-attachments/assets/a0f38159-b31d-4a47-97a7-cc230e15d30b")
    }
}
