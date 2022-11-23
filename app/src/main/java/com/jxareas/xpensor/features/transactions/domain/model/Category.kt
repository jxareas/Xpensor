package com.jxareas.xpensor.features.transactions.domain.model

import android.os.Parcelable
import com.jxareas.xpensor.core.domain.model.Domain
import kotlinx.parcelize.Parcelize


@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val icon: Int,
    val iconColor: String,
) : Domain, Parcelable