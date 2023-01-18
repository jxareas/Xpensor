package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddTransactionUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {

    suspend fun invoke(transaction: Transaction, accountId: Int, categoryId: Int) {
        accountRepository.getAccountById(accountId)?.let { account ->
            val amount = account.amount - transaction.amount
            accountRepository.updateAccountAmount(accountId, amount)
            transactionRepository.insertTransaction(transaction, accountId, categoryId)
        }
    }

    suspend fun invoke(details: TransactionDetails) {
        accountRepository.getAccountById(details.account.id)?.let { account ->
            val amount = account.amount - details.transaction.amount
            accountRepository.updateAccountAmount(account.id, amount)
            transactionRepository.insertTransaction(details)
        }
    }


}
