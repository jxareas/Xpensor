package com.jxareas.xpensor.features.transactions.domain.model

import com.jxareas.xpensor.common.utils.DateUtils
import java.time.LocalDate
import java.time.LocalTime

data class Transaction(
    val id: Int = 0,
    val note: String,
    val amount: Double,
    val date: LocalDate = DateUtils.getCurrentLocalDate(),
    val time: LocalTime = DateUtils.getCurrentLocalTime(),
)
