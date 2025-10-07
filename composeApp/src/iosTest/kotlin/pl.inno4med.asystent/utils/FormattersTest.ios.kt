package pl.inno4med.asystent.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.localeWithLocaleIdentifier
import platform.Foundation.setDefaultTimeZone
import platform.Foundation.timeZoneWithName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

class DateTimeFormatterTest {

    val date = LocalDateTime(2025, 9, 29, 12, 33)
    val now = LocalDateTime(2025, 10, 1, 14, 0)
    val timezone = NSTimeZone.timeZoneWithName("GMT+01:00")!!

    @OptIn(ExperimentalForeignApi::class, ExperimentalTime::class)
    @BeforeTest
    fun setup() {
        DateFormatter.testLocale = NSLocale.localeWithLocaleIdentifier("pl_PL")
        NSTimeZone.setDefaultTimeZone(timezone)
        DateFormatter.now = now.toInstant(kotlinx.datetime.TimeZone.of("GMT+01:00")).toNSDate()
    }

    @Test
    fun relativeDate() {
        assertEquals("Przedwczoraj", DateFormatter.relativeDate(date))
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun fullDate() {
        assertEquals("29 września 2025 o 13:33", DateFormatter.fullDate(date))
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun relativeTime() {
        val date = LocalDateTime(2025, 10, 1, 12, 0)
        assertEquals("2 godziny temu", DateFormatter.relativeDate(date))
    }
}
