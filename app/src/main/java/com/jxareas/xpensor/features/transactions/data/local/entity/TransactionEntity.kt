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
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity.Companion.FK_ACCOUNT
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity.Companion.FK_CATEGORY
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = [CategoryEntity.PK],
            childColumns = [FK_CATEGORY],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = [AccountEntity.PK],
            childColumns = [FK_ACCOUNT],
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index(value = [FK_ACCOUNT], name = "IX_FK_account", unique = false),
        Index(value = [FK_CATEGORY], name = "IX_FK_category", unique = false),
    ],
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PK, typeAffinity = ColumnInfo.INTEGER)
    val id: Int = 0,
    @ColumnInfo(name = "note", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.BINARY)
    val note: String,
    @ColumnInfo(name = "amount", typeAffinity = ColumnInfo.REAL)
    val amount: Double,
    @ColumnInfo(name = "date", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    val date: LocalDate = getCurrentLocalDate(),
    @ColumnInfo(name = "time", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    val time: LocalTime = getCurrentLocalTime(),
    @ColumnInfo(name = FK_ACCOUNT, typeAffinity = ColumnInfo.INTEGER)
    val accountId: Int,
    @ColumnInfo(name = FK_CATEGORY, typeAffinity = ColumnInfo.INTEGER)
    val categoryId: Int,
) {
    companion object {
        const val PK = "id"
        const val FK_ACCOUNT = "account_id"
        const val FK_CATEGORY = "category_id"
    }
}
