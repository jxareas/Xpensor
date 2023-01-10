package com.jxareas.xpensor.features.accounts.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountUi(
    val id: Int,
    val name: String,
    val amount: Double,
    val color: String,
) : Parcelable {
    companion object {
        const val EMPTY_ID = 0
    }
}
