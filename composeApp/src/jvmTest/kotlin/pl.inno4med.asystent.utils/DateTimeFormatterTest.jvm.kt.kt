package pl.inno4med.asystent.utils

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import org.junit.After
import org.junit.Before
import java.util.Locale
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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
    @After
    fun tearDown() {
        unmockkObject(Clock.System)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun relativeDate() {
        mockkObject(Clock.System)
        every { Clock.System.now() } returns now.toInstant(kotlinx.datetime.TimeZone.of("GMT+01:00"))

        assertEquals("2 dni temu", DateFormatter.relativeDate(date))
    }

    @Test
    fun fullDate() {
        assertEquals("29 września 2025, 13:33", DateFormatter.fullDate(date))
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun relativeTime() {
        mockkObject(Clock.System)
        every { Clock.System.now() } returns now.toInstant(kotlinx.datetime.TimeZone.of("GMT+01:00"))

        val date = LocalDateTime(2025, 10, 1, 12, 0)
        assertEquals("2 godziny temu", DateFormatter.relativeDate(date))
    }
}
