package com.jxareas.xpensor.features.accounts.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails


object AccountEntityMapper : Mapper<AccountWithDetails, AccountEntity> {

    override fun mapFromDomain(source: AccountWithDetails): AccountEntity =
        AccountEntity(
            id = if (source.id == AccountEntity.EMPTY_ID) null else source.id,
            name = source.name,
            amount = source.amount,
            color = source.color)

    override fun mapToDomain(destination: AccountEntity): AccountWithDetails =
        AccountWithDetails(id = destination.id ?: 0,
            name = destination.name,
            amount = destination.amount,
            color = destination.color)
}