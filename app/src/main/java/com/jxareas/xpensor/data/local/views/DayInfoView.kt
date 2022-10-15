package com.jxareas.xpensor.data.local.views

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.LocalDate

@Entity
data class DayInfoView(
    @ColumnInfo(name = "date")
    val transactionDate: LocalDate,
    @ColumnInfo(name = "amount_per_day")
    val amountPerDay: Double,
)