package com.jxareas.sharedtest.data

import com.jxareas.xpensor.features.accounts.data.mapper.toAccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.Account

val mockAccounts = mockList { index ->
    Account(
        id = index,
        name = "account$index",
        amount = index.toDouble(),
        color = "color$index",
    )
}

val mockAccountEntities = mockAccounts.map(Account::toAccountEntity)
