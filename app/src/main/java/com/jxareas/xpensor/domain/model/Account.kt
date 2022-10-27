package com.jxareas.xpensor.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val id: Int,
    val name: String,
    val amount: Double = 0.0,
    val color: String
) : Domain, Parcelable {
    companion object {
        const val EMPTY_ID = 0
    }
}