package com.jxareas.xpensor.domain.model

class Account(
    val id: Int,
    val name: String,
    val amount: Double = 0.0,
    val color: String
) : Domain