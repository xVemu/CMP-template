package pl.inno4med.asystent.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

actual val platformColorScheme: ColorScheme
    @Composable
    get() {
        val useDarkTheme = isSystemInDarkTheme()

        return when {
            useDarkTheme -> darkScheme
            else -> lightScheme
        }
    }
