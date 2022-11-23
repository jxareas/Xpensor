package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(transaction: TransactionView) {

        val account = accountRepository.getAccountById(transaction.id)
        if (account != null) {
            val updatedAmount = account.amount + transaction.amount
            accountRepository.updateAccountAmount(transaction.accountId, updatedAmount)
            transactionRepository.deleteTransactionById(transaction.id)
        }

    }

}