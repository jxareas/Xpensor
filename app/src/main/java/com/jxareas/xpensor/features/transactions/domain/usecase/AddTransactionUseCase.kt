package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddTransactionUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {

    suspend operator fun invoke(transaction: Transaction) {
        accountRepository.getAccountById(transaction.accountId)?.let { account ->
            val amount = account.amount - transaction.amount
            accountRepository.updateAccountAmount(transaction.accountId, amount)
            transactionRepository.insertTransaction(transaction)
        }
    }

}