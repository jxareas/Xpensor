package com.jxareas.xpensor.features.accounts.presentation.mapper

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

fun AccountWithDetails.toAccountUi(): AccountUi =
    AccountUi(
        id = id,
        name = name,
        amount = amount,
        color = color,
    )

fun AccountUi.toAccountWithDetails(): AccountWithDetails =
    AccountWithDetails(
        id = id ?: AccountUi.EMPTY_ID,
        name = name,
        amount = amount,
        color = color,
    )
