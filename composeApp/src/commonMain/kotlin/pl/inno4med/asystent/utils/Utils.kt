package pl.inno4med.asystent.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.LayoutDirection
import com.eygraber.uri.Uri
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun String.capitalize() = lowercase().replaceFirstChar { it.uppercase() }

@OptIn(ExperimentalTime::class)
fun LocalDateTime.Companion.now() =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

operator fun PaddingValues.plus(other: PaddingValues) =
    PaddingValues(
        top = calculateTopPadding() + other.calculateTopPadding(),
        start = calculateStartPadding(LayoutDirection.Ltr) + other.calculateStartPadding(
            LayoutDirection.Ltr
        ),
        end = calculateEndPadding(LayoutDirection.Ltr) + other.calculateEndPadding(LayoutDirection.Ltr),
        bottom = calculateBottomPadding() + other.calculateBottomPadding(),
    )

typealias UnitCallback = () -> Unit
typealias Setter<T> = (T) -> Unit
typealias UnitComposable = @Composable () -> Unit

expect fun getMapUrl(latitude: Double, longitude: Double, label: String): Uri

expect val platform: Platform

enum class Platform {
    ANDROID, IOS, JVM;

    val isIos: Boolean
        get() = this == IOS

    val isAndroid: Boolean
        get() = this == ANDROID

    val isJvm: Boolean
        get() = this == JVM

    companion object {
        fun currentPlatform(): Platform = platform
    }
}
