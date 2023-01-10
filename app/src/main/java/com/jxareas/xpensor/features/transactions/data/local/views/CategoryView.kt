package com.jxareas.xpensor.features.transactions.data.local.views

import androidx.room.ColumnInfo

data class CategoryView(
    val id: Int,
    val name: String,
    val icon: Int,
    @ColumnInfo(name = "icon_color")
    val iconColor: String,
    @ColumnInfo(name = "category_amount")
    val amount: Double
)
