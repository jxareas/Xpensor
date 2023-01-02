package com.jxareas.xpensor.features.accounts.presentation.mapper

import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi

fun AccountWithDetails.asAccountDetailsUi(): AccountWithDetailsUi =
    AccountWithDetailsUi(
        id = id,
        name = name,
        amount = amount,
        color = color
    )

fun AccountWithDetailsUi.asAccountUi(): AccountUi =
    AccountUi(id ?: AccountWithDetailsUi.EMPTY_ID, name)

fun AccountWithDetailsUi.asAccountWithDetails(): AccountWithDetails =
    AccountWithDetails(
        id = id ?: AccountWithDetailsUi.EMPTY_ID,
        name = name,
        amount = amount,
        color = color,
    )

fun Account.asAccountUi(): AccountUi = AccountUi(id, name)

fun AccountUi.asAccount(): Account = Account(id, name)
