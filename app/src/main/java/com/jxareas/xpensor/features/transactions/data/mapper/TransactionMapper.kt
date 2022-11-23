package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.core.domain.mapper.DomainMapper
import com.jxareas.xpensor.features.transactions.domain.model.Transaction

object TransactionMapper : DomainMapper<TransactionEntity, Transaction> {
    override fun toDomain(entity: TransactionEntity): Transaction =
        Transaction(
            entity.id,
            entity.note,
            entity.amount,
            entity.date,
            entity.time,
            entity.accountId,
            entity.categoryId,
        )

    override fun fromDomain(domain: Transaction): TransactionEntity =
        TransactionEntity(domain.id,
            domain.note,
            domain.amount,
            domain.date,
            domain.time,
            domain.accountId,
            domain.categoryId)

}