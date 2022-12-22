package com.jxareas.xpensor.features.transactions.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryUi(
    val id: Int,
    val name: String,
    val icon: Int,
    val iconColor: String,
) : Parcelable
