package com.jxareas.xpensor.core.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalTime

class TimeConverter {

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String {
        return time.toString()
    }

    @TypeConverter
    fun toLocalTime(data: String): LocalTime {
        return LocalTime.parse(data)
    }

}