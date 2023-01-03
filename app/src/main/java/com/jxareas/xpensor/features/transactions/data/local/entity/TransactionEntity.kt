package com.jxareas.xpensor.features.transactions.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jxareas.xpensor.common.utils.DateUtils.getCurrentLocalDate
import com.jxareas.xpensor.common.utils.DateUtils.getCurrentLocalTime
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("account_id"), Index("category_id")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val note: String,
    val amount: Double,
    val date: LocalDate = getCurrentLocalDate(),
    val time: LocalTime = getCurrentLocalTime(),
    @ColumnInfo(name = "account_id")
    val accountId: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int
)
