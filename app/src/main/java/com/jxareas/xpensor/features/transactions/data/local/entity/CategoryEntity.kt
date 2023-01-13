package com.jxareas.xpensor.features.transactions.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PK, typeAffinity = ColumnInfo.INTEGER)
    val id: Int = 0,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.BINARY)
    val name: String,
    @ColumnInfo(name = "icon", typeAffinity = ColumnInfo.INTEGER)
    val icon: Int,
    @ColumnInfo(name = "icon_color", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.BINARY)
    val iconColor: String,
) {
    companion object {
        const val PK = "id"
    }
}
