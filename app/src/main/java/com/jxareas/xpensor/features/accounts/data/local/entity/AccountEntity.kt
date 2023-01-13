package com.jxareas.xpensor.features.accounts.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PK, typeAffinity = ColumnInfo.INTEGER)
    val id: Int = 0,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.BINARY)
    val name: String,
    @ColumnInfo(name = "amount", typeAffinity = ColumnInfo.REAL)
    val amount: Double = 0.0,
    @ColumnInfo(name = "color", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.BINARY)
    val color: String,
) {
    companion object {
        const val PK = "id"
    }
}
