package com.jxareas.xpensor.features.accounts.presentation.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount

object AccountUiMapper : Mapper<AccountWithDetails, UiAccount> {
    override fun mapFromDomain(source: AccountWithDetails): UiAccount =
        UiAccount(
            id = source.id,
            name = source.name,
            amount = source.amount,
            color = source.color)

    override fun mapToDomain(destination: UiAccount): AccountWithDetails =
        AccountWithDetails(
            id = destination.id ?: UiAccount.EMPTY_ID,
            name = destination.name,
            amount = destination.amount,
            color = destination.color,
        )

}