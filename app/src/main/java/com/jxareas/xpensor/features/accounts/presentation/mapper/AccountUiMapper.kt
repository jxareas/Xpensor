package com.jxareas.xpensor.features.accounts.presentation.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem

object AccountUiMapper : Mapper<AccountWithDetails, AccountListItem> {
    override fun mapTo(source: AccountWithDetails): AccountListItem =
        AccountListItem(
            id = source.id,
            name = source.name,
            amount = source.amount,
            color = source.color)

    override fun mapFrom(destination: AccountListItem): AccountWithDetails =
        AccountWithDetails(
            id = destination.id ?: AccountListItem.EMPTY_ID,
            name = destination.name,
            amount = destination.amount,
            color = destination.color,
        )

}