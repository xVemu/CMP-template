package pl.inno4med.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.architect.kmpessentials.connectivity.KmpConnectivity

@Composable
fun rememberNetwork(onAvailable: (() -> Unit)? = null): Boolean {
    val callback by rememberUpdatedState(onAvailable)
    var hasNetwork by rememberSaveable { mutableStateOf(true) }

    // TODO make single instance and use CompositionLocal?
    LaunchedEffect(Unit) {
        KmpConnectivity.listenToConnectionChange { isAvailable ->
            if (isAvailable && !hasNetwork) callback?.invoke()

            hasNetwork = isAvailable
        }
    }

    return hasNetwork
}
