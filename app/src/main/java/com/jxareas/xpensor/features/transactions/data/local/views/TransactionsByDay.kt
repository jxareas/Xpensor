package com.jxareas.xpensor.features.transactions.data.local.views

import androidx.room.ColumnInfo
import java.time.LocalDate

data class TransactionsByDay(
    @ColumnInfo(name = "date")
    val transactionDate: LocalDate,
    @ColumnInfo(name = "amount_per_day")
    val amountPerDay: Double,
)
