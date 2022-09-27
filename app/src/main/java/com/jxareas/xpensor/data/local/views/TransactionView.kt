package com.jxareas.xpensor.data.local.views

import androidx.room.ColumnInfo
import java.time.LocalDate
import java.time.LocalTime

data class TransactionView(
    val id: Int,
    val note: String,
    val amount: Double,
    val date: LocalDate,
    val time: LocalTime,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    @ColumnInfo(name = "account_id")
    val accountId: Int,
    @ColumnInfo(name = "account_name")
    val accountName: String,
    val icon: Int,
    @ColumnInfo(name = "icon_color")
    val iconColor: String
)