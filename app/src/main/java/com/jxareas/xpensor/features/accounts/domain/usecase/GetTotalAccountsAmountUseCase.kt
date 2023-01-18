package com.jxareas.xpensor.features.accounts.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalAccountsAmountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {

    fun invoke(): Flow<Double> =
        accountRepository.getTotalAccountsAmount()

}
