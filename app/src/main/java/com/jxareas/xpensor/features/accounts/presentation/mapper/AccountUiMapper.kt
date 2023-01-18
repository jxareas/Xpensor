package com.jxareas.xpensor.features.accounts.presentation.mapper

import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

fun Account.toAccountUi(): AccountUi =
    AccountUi(
        id = id,
        name = name,
        amount = amount,
        color = color,
    )

fun AccountUi.toAccount(): Account =
    Account(
        id = id,
        name = name,
        amount = amount,
        color = color,
    )
