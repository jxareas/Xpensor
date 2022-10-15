package com.jxareas.xpensor.data.mapper

import com.jxareas.xpensor.data.local.model.TransactionEntity
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Transaction

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