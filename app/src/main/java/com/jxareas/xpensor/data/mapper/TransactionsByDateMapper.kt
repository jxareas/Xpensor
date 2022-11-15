package com.jxareas.xpensor.data.mapper

import com.jxareas.xpensor.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.TransactionsByDate

object TransactionsByDateMapper : DomainMapper<TransactionsByDateView, TransactionsByDate> {
    override fun toDomain(entity: TransactionsByDateView): TransactionsByDate =
        TransactionsByDate(entity.transactionDate, entity.amountPerDay)

    override fun fromDomain(domain: TransactionsByDate): TransactionsByDateView =
        TransactionsByDateView(domain.transactionDate, domain.amountPerDay)
}