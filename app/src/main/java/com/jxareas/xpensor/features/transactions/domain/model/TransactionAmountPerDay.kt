package com.jxareas.xpensor.features.transactions.domain.model

import com.jxareas.xpensor.core.domain.model.Domain
import java.time.LocalDate

data class TransactionAmountPerDay(
    val transactionDate: LocalDate,
    val amountPerDay: Double,
) : Domain