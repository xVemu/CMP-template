package pl.inno4med.asystent.theme

import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual val platformColorScheme: ColorScheme
    @Composable
    get() {
        val useDarkTheme = isSystemInDarkTheme()
        val context = LocalContext.current
        val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

        return when {
            dynamicColor && useDarkTheme -> dynamicDarkColorScheme(context)
            dynamicColor -> dynamicLightColorScheme(context)
            context.isHighContrastEnabled && useDarkTheme -> highContrastDarkColorScheme
            context.isHighContrastEnabled -> highContrastLightColorScheme
            context.isMediumContrastEnabled && useDarkTheme -> mediumContrastDarkColorScheme
            context.isMediumContrastEnabled -> mediumContrastLightColorScheme
            useDarkTheme -> darkScheme
            else -> lightScheme
        }
    }

private val Context.isMediumContrastEnabled
    get() = Settings.Secure.getFloat(contentResolver, "contrast_level", 0F) >= .5F

private val Context.isHighContrastEnabled
    get() = Settings.Secure.getFloat(contentResolver, "contrast_level", 0F) >= 1F
