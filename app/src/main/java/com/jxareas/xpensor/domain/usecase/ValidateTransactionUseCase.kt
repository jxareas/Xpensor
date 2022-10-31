package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.Transaction
import com.jxareas.xpensor.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ValidateTransactionUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(transaction: Transaction) =
        accountRepository.getAccountById(transaction.accountId).let { account ->
            if (account != null)
                return account.amount > transaction.amount
            else false
        }

}