package com.jxareas.xpensor.domain.model

import java.time.LocalDate

data class TransactionsByDate(
    val transactionDate: LocalDate,
    val amountPerDay: Double,
) : Domain