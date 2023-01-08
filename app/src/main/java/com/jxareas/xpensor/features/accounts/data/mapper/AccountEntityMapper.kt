package com.jxareas.xpensor.features.accounts.data.mapper

import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

fun AccountWithDetails.toAccountEntity() : AccountEntity =
    AccountEntity(
            id = if (id == AccountEntity.EMPTY_ID) null else id,
            name = name,
            amount = amount,
            color = color
        )

fun AccountEntity.toAccountWithDetails() : AccountWithDetails =
    AccountWithDetails(
            id = id ?: 0,
            name = name,
            amount = amount,
            color = color
        )
