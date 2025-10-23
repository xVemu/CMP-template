package pl.inno4med.asystent.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIAccessibilityDarkerSystemColorsEnabled
import platform.darwin.NSObject

actual val platformColorScheme: ColorScheme
    @Composable
    get() {
        val useDarkTheme = isSystemInDarkTheme()
        val highContrast = isHighContrast()

        return when {
            highContrast && useDarkTheme -> highContrastDarkColorScheme
            highContrast -> highContrastLightColorScheme
            useDarkTheme -> darkScheme
            else -> lightScheme
        }
    }

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun isHighContrast(): Boolean {
    var isHighContrast by remember {
        mutableStateOf(UIAccessibilityDarkerSystemColorsEnabled())
    }

    DisposableEffect(Unit) {

        val observer = HighContrastObserver {
            isHighContrast = UIAccessibilityDarkerSystemColorsEnabled()
        }

        onDispose {
            observer.dispose()
        }
    }

    return isHighContrast
}

@OptIn(ExperimentalForeignApi::class)
class HighContrastObserver(val onChange: () -> Unit) : NSObject() {

    init {
        val center = NSNotificationCenter.defaultCenter
        center.addObserver(
            observer = this,
            selector = NSSelectorFromString("highContrastChanged"),
            name = "UIAccessibilityDarkerSystemColorsStatusDidChangeNotification",
            null
        )
    }

    @OptIn(BetaInteropApi::class)
    @ObjCAction
    fun highContrastChanged() {
        onChange()
    }

    fun dispose() {
        val center = NSNotificationCenter.defaultCenter
        center.removeObserver(this)
    }
}
