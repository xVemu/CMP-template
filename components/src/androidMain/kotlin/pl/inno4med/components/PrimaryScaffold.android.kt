package pl.inno4med.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal actual fun Modifier.platformNavigationBarInsets(): Modifier = this.windowInsetsPadding(
    WindowInsets.safeDrawing.only(
        WindowInsetsSides.Start
    )
)

@Composable
internal actual fun Modifier.platformEndBodyInsets() = this.windowInsetsPadding(
    WindowInsets.safeDrawing.only(
        WindowInsetsSides.End
    )
)
