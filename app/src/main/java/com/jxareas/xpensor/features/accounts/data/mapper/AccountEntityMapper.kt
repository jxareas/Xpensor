package com.jxareas.xpensor.features.accounts.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails


object AccountEntityMapper : Mapper<AccountEntity, AccountWithDetails> {
    override fun mapTo(source: AccountEntity): AccountWithDetails =
        AccountWithDetails(id = source.id ?: 0,
            name = source.name,
            amount = source.amount,
            color = source.color)

    override fun mapFrom(destination: AccountWithDetails): AccountEntity =
        AccountEntity(
            id = if (destination.id == AccountEntity.EMPTY_ID) null else destination.id,
            name = destination.name,
            amount = destination.amount,
            color = destination.color)
}