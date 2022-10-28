package com.jxareas.xpensor.data.mapper

import com.jxareas.xpensor.data.local.model.AccountEntity
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Account


object AccountMapper : DomainMapper<AccountEntity, Account> {
    override fun toDomain(entity: AccountEntity): Account =
        Account(id = entity.id ?: 0,
            name = entity.name,
            amount = entity.amount,
            color = entity.color)

    override fun fromDomain(domain: Account): AccountEntity =
        AccountEntity(
            id = if (domain.id == Account.EMPTY_ID) null else domain.id,
            name = domain.name,
            amount = domain.amount,
            color = domain.color)
}