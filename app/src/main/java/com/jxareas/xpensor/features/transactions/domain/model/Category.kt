package com.jxareas.xpensor.features.transactions.domain.model

import com.jxareas.xpensor.core.domain.model.Domain


data class Category(
    val id: Int,
    val name: String,
    val icon: Int,
    val iconColor: String,
) : Domain