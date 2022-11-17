package com.jxareas.xpensor.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class TransactionWithDetails(
    val id: Int,
    val note: String,
    val amount: Double,
    val date: LocalDate,
    val time: LocalTime,
    val category: Category,
    val account: Account,
) : Domain