package pl.inno4med.asystent.utils

import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

actual object DateFormatter {
    @OptIn(markerClass = [ExperimentalTime::class])
    actual fun relativeDate(date: LocalDateTime): String = DateUtils.getRelativeTimeSpanString(
        date.toInstant(UtcOffset.ZERO).toEpochMilliseconds(),
        Clock.System.now().toEpochMilliseconds(),
        DateUtils.DAY_IN_MILLIS
    ).toString()

    @RequiresApi(Build.VERSION_CODES.O)
    actual fun fullDate(date: LocalDateTime): String =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
            .format(
                date.toJavaLocalDateTime().atOffset(ZoneOffset.UTC)
                    .atZoneSameInstant(ZoneId.systemDefault())
            )
}
