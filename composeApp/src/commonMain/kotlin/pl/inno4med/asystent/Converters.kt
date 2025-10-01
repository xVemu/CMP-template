package pl.inno4med.asystent

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class Converters {
    @TypeConverter
    fun fromISO(value: String) = LocalDateTime.parse(value)

    @TypeConverter
    fun toISO(date: LocalDateTime) = date.toString()

    @TypeConverter
    fun fromTime(time: String) = LocalTime.parse(time)

    @TypeConverter
    fun toTime(time: LocalTime) = time.toString()
}
