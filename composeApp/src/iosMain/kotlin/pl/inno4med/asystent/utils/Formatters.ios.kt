package pl.inno4med.asystent.utils

import androidx.annotation.VisibleForTesting
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterLongStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSFormattingContextBeginningOfSentence
import platform.Foundation.NSLocale
import platform.Foundation.NSRelativeDateTimeFormatter
import platform.Foundation.NSRelativeDateTimeFormatterStyleNamed
import platform.Foundation.now
import kotlin.time.ExperimentalTime

actual object DateFormatter {

    @VisibleForTesting()
    var testLocale: NSLocale? = null

    @VisibleForTesting()
    var now: NSDate? = null

    @OptIn(markerClass = [ExperimentalTime::class])
    actual fun relativeDate(date: LocalDateTime): String {
        return NSRelativeDateTimeFormatter().apply {
            if (testLocale != null) locale = testLocale!!
            dateTimeStyle = NSRelativeDateTimeFormatterStyleNamed
            formattingContext = NSFormattingContextBeginningOfSentence // Capitalize first letter
        }.localizedStringForDate(date.toInstant(UtcOffset.ZERO).toNSDate(), now ?: NSDate.now)
    }

    @OptIn(ExperimentalTime::class)
    actual fun fullDate(date: LocalDateTime): String = NSDateFormatter().apply {
        dateStyle = NSDateFormatterLongStyle
        timeStyle = NSDateFormatterShortStyle
        if (testLocale != null) locale = testLocale!!
    }.stringFromDate(date.toInstant(UtcOffset.ZERO).toNSDate())
}
