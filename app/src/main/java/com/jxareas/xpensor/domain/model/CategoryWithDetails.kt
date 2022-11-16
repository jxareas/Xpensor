package com.jxareas.xpensor.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryWithDetails(
    val category: Category,
    val amount: Double,
) : Domain, Parcelable