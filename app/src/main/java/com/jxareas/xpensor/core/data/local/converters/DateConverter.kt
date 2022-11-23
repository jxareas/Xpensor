package com.jxareas.xpensor.core.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(data: String): LocalDate {
        return LocalDate.parse(data)
    }

}