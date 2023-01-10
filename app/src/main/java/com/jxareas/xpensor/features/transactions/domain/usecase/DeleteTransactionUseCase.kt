package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(details: TransactionDetails) {

        val account = accountRepository.getAccountById(details.account.id)
        if (account != null) {
            val updatedAmount = account.amount + details.transaction.amount
            accountRepository.updateAccountAmount(details.account.id, updatedAmount)
            transactionRepository.deleteTransactionById(details.transaction.id)
        }
    }
}
