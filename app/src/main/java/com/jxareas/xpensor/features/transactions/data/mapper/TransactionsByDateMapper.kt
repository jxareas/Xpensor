package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.domain.model.TransactionsByDate

object TransactionsByDateMapper : Mapper<TransactionsByDateView, TransactionsByDate> {
    override fun mapTo(source: TransactionsByDateView): TransactionsByDate =
        TransactionsByDate(source.transactionDate, source.amountPerDay)

    override fun mapFrom(destination: TransactionsByDate): TransactionsByDateView =
        TransactionsByDateView(destination.transactionDate, destination.amountPerDay)
}