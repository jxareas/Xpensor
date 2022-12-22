package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ValidateTransactionUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(transaction: Transaction) =
        accountRepository.getAccountById(transaction.accountId).let { account ->
            if (account != null)
                account.amount > transaction.amount
            else false
        }
}
