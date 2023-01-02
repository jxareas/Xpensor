package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(transaction: TransactionWithDetails) {

        val account = accountRepository.getAccountById(transaction.account.id)
        if (account != null) {
            val updatedAmount = account.amount + transaction.amount
            accountRepository.updateAccountAmount(transaction.account.id, updatedAmount)
            transactionRepository.deleteTransactionById(transaction.id)
        }
    }
}
