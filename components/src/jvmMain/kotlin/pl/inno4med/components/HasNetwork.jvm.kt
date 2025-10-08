package pl.inno4med.components

import dev.jordond.connectivity.Connectivity

actual fun createConnectivity() = Connectivity {
    autoStart = true
    pollingIntervalMs = 10.seconds
}
