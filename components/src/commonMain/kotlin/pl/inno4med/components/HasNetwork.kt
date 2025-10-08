package pl.inno4med.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.collectLatest

@Composable
fun rememberNetwork(onAvailable: (() -> Unit)? = null): Boolean {
    val callback by rememberUpdatedState(onAvailable)
    var hasNetwork by rememberSaveable { mutableStateOf(true) }

    // TODO make single instance and use CompositionLocal?
    val connectivity = remember { createConnectivity() }

    DisposableEffect(Unit) {
        onDispose {
            connectivity.stop()
        }
    }

    LaunchedEffect(Unit) {
        connectivity.statusUpdates.collectLatest { newStatus ->
            val isConnected = newStatus is Connectivity.Status.Connected

            if (isConnected && !hasNetwork) callback?.invoke()

            hasNetwork = isConnected
        }
    }

    return hasNetwork
}

expect fun createConnectivity(): Connectivity
