package com.jxareas.xpensor.features.accounts.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val amount: Double = 0.0,
    val color: String,
) : Parcelable {
    companion object {
        const val EMPTY_ID = -287
    }
}
