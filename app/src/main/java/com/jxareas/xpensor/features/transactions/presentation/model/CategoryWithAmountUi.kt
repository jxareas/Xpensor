package com.jxareas.xpensor.features.transactions.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryWithAmountUi(
    val categoryUi: CategoryUi,
    val amount: Double,
) : Parcelable
