package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.domain.model.Transaction

object TransactionMapper : Mapper<Transaction, TransactionEntity> {

    override fun mapFromDomain(source: Transaction): TransactionEntity =
        TransactionEntity(source.id,
            source.note,
            source.amount,
            source.date,
            source.time,
            source.accountId,
            source.categoryId)

    override fun mapToDomain(destination: TransactionEntity): Transaction =
        Transaction(
            destination.id,
            destination.note,
            destination.amount,
            destination.date,
            destination.time,
            destination.accountId,
            destination.categoryId,
        )

}