package com.jxareas.xpensor.features.accounts.data.mapper

import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.Account

fun Account.toAccountEntity() : AccountEntity =
    AccountEntity(
            id = if (id == AccountEntity.EMPTY_ID) null else id,
            name = name,
            amount = amount,
            color = color
        )

fun AccountEntity.toAccountWithDetails() : Account =
    Account(
            id = id ?: 0,
            name = name,
            amount = amount,
            color = color
        )
