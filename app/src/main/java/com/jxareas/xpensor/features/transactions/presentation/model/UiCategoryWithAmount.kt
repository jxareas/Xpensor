package com.jxareas.xpensor.features.transactions.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiCategoryWithAmount(
    val category: UiCategory,
    val amount: Double,
) : Parcelable