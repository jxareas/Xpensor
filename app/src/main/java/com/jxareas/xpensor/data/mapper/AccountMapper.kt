package com.jxareas.xpensor.data.mapper

import com.jxareas.xpensor.data.local.model.AccountEntity
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Account
import javax.inject.Singleton


object AccountMapper : DomainMapper<AccountEntity, Account> {
    override fun toDomain(entity: AccountEntity): Account =
        Account(entity.id ?: 0, entity.name, entity.amount, entity.color)

    override fun fromDomain(domain: Account): AccountEntity =
        AccountEntity(domain.id, domain.name, domain.amount, domain.color)
}