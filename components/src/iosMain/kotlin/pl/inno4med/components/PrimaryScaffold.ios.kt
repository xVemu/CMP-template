package pl.inno4med.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// For some reason iOS has safeDrawing padding at start, even tho notch is at right side
@Composable
actual fun Modifier.platformNavigationBarInsets() = this.windowInsetsPadding(
    WindowInsets.displayCutout.only(
        WindowInsetsSides.Start
    )
).consumeWindowInsets(WindowInsets.safeDrawing.only(WindowInsetsSides.Start))

@Composable
actual fun Modifier.platformEndBodyInsets() = this.windowInsetsPadding(
    WindowInsets.displayCutout.only(
        WindowInsetsSides.End
    )
)
