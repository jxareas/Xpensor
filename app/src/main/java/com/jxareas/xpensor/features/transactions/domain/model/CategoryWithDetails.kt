package com.jxareas.xpensor.features.transactions.domain.model

import android.os.Parcelable
import com.jxareas.xpensor.core.domain.model.Domain
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryWithDetails(
    val category: Category,
    val amount: Double,
) : Domain, Parcelable