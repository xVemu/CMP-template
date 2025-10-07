package pl.inno4med.asystent.utils

import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

expect object DateFormatter {
    /**
     * Example: 'today', 'yesterday'
     * */
    @OptIn(ExperimentalTime::class)
    fun relativeDate(date: LocalDateTime): String

    /**
     * Example: '16 Nov 2023, 09:23:11'
     * */
    fun fullDate(date: LocalDateTime): String
}

fun String.asPhoneNumber() = replace(".{3}".toRegex(), "$0 ").trimEnd()
