package com.jxareas.sharedtest.data

import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountUi
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import kotlinx.coroutines.flow.flowOf


val mockAccountDetailsUi = List(NUMBER_OF_MOCKS) { index ->
    AccountWithDetailsUi(index, "account$index", index.toDouble(), "color$index")
}

val mockAccountDetails = mockAccountDetailsUi.map(AccountWithDetailsUi::asAccountWithDetails)

val mockAccountUi = mockAccountDetailsUi.map(AccountWithDetailsUi::asAccountUi)

val mockAccountDetailsFlow = flowOf(mockAccountDetails)

