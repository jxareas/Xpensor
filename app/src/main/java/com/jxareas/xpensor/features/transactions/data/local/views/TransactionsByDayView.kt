package com.jxareas.xpensor.features.transactions.data.local.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import java.time.LocalDate

@DatabaseView(
    value = """
    SELECT date, SUM(amount) AS amount_per_day
    FROM transactions
    """,
    viewName = "view_transactions_by_day"
)
data class TransactionsByDayView(
    @ColumnInfo(name = "date")
    val transactionDate: LocalDate,
    @ColumnInfo(name = "amount_per_day")
    val amountPerDay: Double,
)
