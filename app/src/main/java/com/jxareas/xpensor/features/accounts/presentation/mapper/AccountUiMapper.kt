package com.jxareas.xpensor.features.accounts.presentation.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountUiMapper @Inject constructor() : Mapper<AccountWithDetails, AccountUi> {
    override fun mapFromDomain(source: AccountWithDetails): AccountUi =
        AccountUi(
            id = source.id,
            name = source.name,
            amount = source.amount,
            color = source.color)

    override fun mapToDomain(destination: AccountUi): AccountWithDetails =
        AccountWithDetails(
            id = destination.id ?: AccountUi.EMPTY_ID,
            name = destination.name,
            amount = destination.amount,
            color = destination.color,
        )

}