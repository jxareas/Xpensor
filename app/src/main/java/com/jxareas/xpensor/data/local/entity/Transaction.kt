package com.jxareas.xpensor.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.jxareas.xpensor.utils.DateUtils.getCurrentLocalDate
import com.jxareas.xpensor.utils.DateUtils.getCurrentLocalTime
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = CASCADE
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val note: String,
    val amount: Double,
    val date: LocalDate = getCurrentLocalDate(),
    val time: LocalTime = getCurrentLocalTime(),
    val accountId: Int,
    val categoryId: Int
)