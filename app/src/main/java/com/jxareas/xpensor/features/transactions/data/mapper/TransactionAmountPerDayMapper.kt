package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import javax.inject.Inject

class TransactionAmountPerDayMapper @Inject constructor() :
    Mapper<TransactionAmountPerDay, TransactionsByDateView> {

    override fun mapFromDomain(source: TransactionAmountPerDay): TransactionsByDateView =
        TransactionsByDateView(source.transactionDate, source.amountPerDay)

    override fun mapToDomain(destination: TransactionsByDateView): TransactionAmountPerDay =
        TransactionAmountPerDay(destination.transactionDate, destination.amountPerDay)
}
