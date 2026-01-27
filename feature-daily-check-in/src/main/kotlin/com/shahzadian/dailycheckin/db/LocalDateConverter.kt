package com.shahzadian.dailycheckin.db

import androidx.room.TypeConverter
import java.time.LocalDate

internal class LocalDateConverter {

    @TypeConverter
    fun fromMillis(millis: Long): LocalDate {
        return LocalDate.ofEpochDay(millis)
    }

    @TypeConverter
    fun toMillis(date: LocalDate): Long {
        return date.toEpochDay()
    }
}