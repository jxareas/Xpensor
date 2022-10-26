package com.jxareas.xpensor.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Account(
    val id: Int,
    val name: String,
    val amount: Double = 0.0,
    val color: String
) : Domain, Parcelable