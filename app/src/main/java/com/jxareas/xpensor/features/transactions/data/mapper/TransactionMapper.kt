package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.domain.model.Transaction

object TransactionMapper : Mapper<TransactionEntity, Transaction> {
    override fun mapTo(source: TransactionEntity): Transaction =
        Transaction(
            source.id,
            source.note,
            source.amount,
            source.date,
            source.time,
            source.accountId,
            source.categoryId,
        )

    override fun mapFrom(destination: Transaction): TransactionEntity =
        TransactionEntity(destination.id,
            destination.note,
            destination.amount,
            destination.date,
            destination.time,
            destination.accountId,
            destination.categoryId)

}