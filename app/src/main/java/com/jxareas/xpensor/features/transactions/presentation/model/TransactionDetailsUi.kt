package com.jxareas.xpensor.features.transactions.presentation.model

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import java.time.LocalDate
import java.time.LocalTime

data class TransactionDetailsUi(
    val id: Int,
    val note: String,
    val amount: Double,
    val date: LocalDate,
    val time: LocalTime,
    val category: CategoryUi,
    val account: AccountUi,
) {
    val accountName : String
        get() = account.name

    val categoryName : String
        get() = category.name

}