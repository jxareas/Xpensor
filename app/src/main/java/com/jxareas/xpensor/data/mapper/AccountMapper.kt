package com.jxareas.xpensor.data.mapper

import com.jxareas.xpensor.data.local.model.AccountEntity
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.AccountWithDetails


object AccountMapper : DomainMapper<AccountEntity, AccountWithDetails> {
    override fun toDomain(entity: AccountEntity): AccountWithDetails =
        AccountWithDetails(id = entity.id ?: 0,
            name = entity.name,
            amount = entity.amount,
            color = entity.color)

    override fun fromDomain(domain: AccountWithDetails): AccountEntity =
        AccountEntity(
            id = if (domain.id == AccountWithDetails.EMPTY_ID) null else domain.id,
            name = domain.name,
            amount = domain.amount,
            color = domain.color)
}