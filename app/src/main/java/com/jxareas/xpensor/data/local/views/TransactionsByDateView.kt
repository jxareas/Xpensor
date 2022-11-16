package com.jxareas.xpensor.data.local.views

import androidx.room.ColumnInfo
import java.time.LocalDate

data class TransactionsByDateView(
    @ColumnInfo(name = "date")
    val transactionDate: LocalDate,
    @ColumnInfo(name = "amount_per_day")
    val amountPerDay: Double,
)