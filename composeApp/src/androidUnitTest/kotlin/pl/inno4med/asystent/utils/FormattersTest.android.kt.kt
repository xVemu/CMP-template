package pl.inno4med.asystent.utils

import io.mockk.every
import io.mockk.mockkObject
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Locale
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@RunWith(RobolectricTestRunner::class)
class DateTimeFormatterTest {

    val date = LocalDateTime(2025, 9, 29, 12, 33)
    val now = LocalDateTime(2025, 10, 1, 14, 0)
    val timezone: TimeZone = TimeZone.getTimeZone("GMT+01:00")

    @Before
    fun setup() {
        Locale.setDefault(Locale.of("pl", "PL"))
        TimeZone.setDefault(timezone)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun relativeDate() {
        mockkObject(Clock.System)
        every { Clock.System.now() } returns now.toInstant(kotlinx.datetime.TimeZone.of("GMT+01:00"))

        assertEquals("Przedwczoraj", DateFormatter.relativeDate(date))
    }

    @Test
    fun fullDate() {
        assertEquals("29 września 2025, 13:33", DateFormatter.fullDate(date))
    }
}
