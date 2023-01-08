package com.jxareas.xpensor.features.accounts.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountWithDetails(
    val id: Int,
    val name: String,
    val amount: Double = 0.0,
    val color: String,
) : Parcelable
