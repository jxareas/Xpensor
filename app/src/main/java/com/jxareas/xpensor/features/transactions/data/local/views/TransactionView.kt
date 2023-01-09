package com.jxareas.xpensor.features.transactions.data.local.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import java.time.LocalDate
import java.time.LocalTime

@DatabaseView(
    value = """
    SELECT transactions.id AS transaction_id, transactions.note AS transaction_note,
    transactions.amount AS transaction_amount, transactions.date AS transaction_date,
    transactions.time AS transaction_time,
    accounts.id AS account_id,   accounts.name AS account_name, accounts.amount AS account_amount,
    accounts.color AS account_color,
    categories.id AS category_id, categories.name AS category_name,
    categories.icon AS category_icon, categories.icon_color AS category_icon_color
    FROM transactions
    JOIN accounts ON accounts.id = transactions.account_id
    JOIN categories ON categories.id = transactions.category_id
    """,
    viewName = "view_transactions",
)
data class TransactionView(
    @ColumnInfo(name = "transaction_id")
    val id: Int,
    @ColumnInfo(name = "transaction_note")
    val note: String,
    @ColumnInfo(name = "transaction_amount")
    val amount: Double,
    @ColumnInfo(name = "transaction_date")
    val date: LocalDate,
    @ColumnInfo(name = "transaction_time")
    val time: LocalTime,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    @ColumnInfo(name = "category_icon")
    val icon: Int,
    @ColumnInfo(name = "category_icon_color")
    val iconColor: String,
    @ColumnInfo(name = "account_id")
    val accountId: Int,
    @ColumnInfo(name = "account_name")
    val accountName: String,
    @ColumnInfo(name = "account_amount")
    val accountAmount: Double,
    @ColumnInfo(name = "account_color")
    val accountColor: String,
)
