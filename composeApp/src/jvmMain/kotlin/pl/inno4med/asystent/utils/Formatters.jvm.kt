package pl.inno4med.asystent.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.ocpsoft.prettytime.PrettyTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant

actual object DateFormatter {
    @OptIn(markerClass = [ExperimentalTime::class])
    actual fun relativeDate(date: LocalDateTime): String =
        PrettyTime(Clock.System.now().toJavaInstant()).format(date.toJavaLocalDateTime())

    actual fun fullDate(date: LocalDateTime): String = DateTimeFormatter.ofLocalizedDateTime(
        FormatStyle.LONG, FormatStyle.SHORT
    )
        .format(
            date.toJavaLocalDateTime().atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault())
        )
}
