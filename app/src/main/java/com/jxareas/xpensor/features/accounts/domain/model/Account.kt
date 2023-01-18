package com.jxareas.xpensor.features.accounts.domain.model

data class Account(
    val id: Int,
    val name: String,
    val amount: Double = 0.0,
    val color: String,
)
