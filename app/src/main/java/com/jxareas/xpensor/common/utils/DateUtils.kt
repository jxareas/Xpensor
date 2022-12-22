package com.jxareas.xpensor.common.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.*
import java.util.*
import kotlin.math.absoluteValue

object DateUtils {

    val DEFAULT_LOCAL_DATE = "2000-01-01".asLocalDate()

    val defaultDateRange: DateRange =
        getCurrentLocalDate() to getCurrentLocalDate()

    fun Double.toAmountFormat(withMinus: Boolean): String =
        DecimalFormat(if (withMinus || this < 0) "—######.##" else "######.##")
            .format(this.absoluteValue)

    fun getCurrentLocalDate(): LocalDate = LocalDate.parse(
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    )

    fun getCurrentLocalTime(): LocalTime = LocalTime.parse(
        SimpleDateFormat("hh:mm:ss", Locale.US).format(Date())
    )

    fun Long.toLocalDate(): LocalDate =
        Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

    fun LocalDate.toMilliseconds(): Long =
        this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

    fun String.asLocalDate(): LocalDate = LocalDate.parse(this)

    const val DAY_IN_MS: Long = 1000 * 60 * 60 * 24
}
