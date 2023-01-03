package com.jxareas.sharedtest.data

import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.data.mapper.asAccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountUi
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import kotlinx.coroutines.flow.flowOf

const val CONSTRAINED_ACCOUNT_ID = 287

val mockAccountDetailsUi = List(NUMBER_OF_MOCKS) { index ->
    val newIndex = index + MOCK_DATA_INDEX_OFFSET
    AccountWithDetailsUi(newIndex, "account$newIndex", newIndex.toDouble(), "color$newIndex")
}

val mockAccountDetails = mockAccountDetailsUi.map(AccountWithDetailsUi::asAccountWithDetails)

val mockAccountEntities = mockAccountDetails.map(AccountWithDetails::asAccountEntity)

val mockAccountEntityConstrained =
    AccountEntity(CONSTRAINED_ACCOUNT_ID, "name", 2000.0, "color")


val mockAccountUi = mockAccountDetailsUi.map(AccountWithDetailsUi::asAccountUi)

val mockAccountDetailsFlow = flowOf(mockAccountDetails)

