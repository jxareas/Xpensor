package com.jxareas.xpensor.features.accounts.data.provider

import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountUi
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import kotlinx.coroutines.flow.flowOf

object MockAccountsProvider {


    val mockAccounts = List(20) { index ->
        AccountWithDetailsUi(index, "accountNo$index", index.toDouble(), "color$index")
    }

    val mockAccountDetailsUi = mockAccounts.first()

    val mockAccountDetails = mockAccountDetailsUi.asAccountWithDetails()

    val mockAccountUi = mockAccountDetailsUi.asAccountUi()

    val mockAccountsFlow = flowOf(mockAccounts)
        .mapList(AccountWithDetailsUi::asAccountWithDetails)

}