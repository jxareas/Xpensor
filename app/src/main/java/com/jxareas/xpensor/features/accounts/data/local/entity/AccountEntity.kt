package com.jxareas.xpensor.features.accounts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val amount: Double = 0.0,
    val color: String,
) {
    companion object {
        const val EMPTY_ID = 0
    }
}
