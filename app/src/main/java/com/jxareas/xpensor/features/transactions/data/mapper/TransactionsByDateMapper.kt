package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.domain.model.TransactionsByDate

object TransactionsByDateMapper : Mapper<TransactionsByDate, TransactionsByDateView> {

    override fun mapFromDomain(source: TransactionsByDate): TransactionsByDateView =
        TransactionsByDateView(source.transactionDate, source.amountPerDay)

    override fun mapToDomain(destination: TransactionsByDateView): TransactionsByDate =
        TransactionsByDate(destination.transactionDate, destination.amountPerDay)
}