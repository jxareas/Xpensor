package com.jxareas.xpensor.features.accounts.data.mapper

import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.Account

fun AccountEntity.toAccount(): Account =
    Account(id, name, amount, color)

fun Account.toAccountEntity(): AccountEntity =
    AccountEntity(
        id = id,
        name = name,
        amount = amount,
        color = color,
    )

fun AccountEntity.toAccountWithDetails(): Account =
    Account(
        id = id,
        name = name,
        amount = amount,
        color = color,
    )
