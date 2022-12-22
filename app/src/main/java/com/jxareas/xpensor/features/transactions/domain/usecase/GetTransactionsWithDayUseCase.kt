package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.common.utils.DateRange
import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.coroutines.flow.first

@Module
@InstallIn(SingletonComponent::class)
class GetTransactionsWithDayUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {

    suspend operator fun invoke(
        transactions: List<TransactionWithDetails>,
        dateRange: DateRange,
        account: AccountWithDetails?,
    ): List<Any> {
        val minDate = dateRange.first ?: DateUtils.DEFAULT_LOCAL_DATE
        val maxDate = dateRange.second ?: DateUtils.getCurrentLocalDate()

        val amountsPerDay = if (account == null)
            repository.getTransactionAmountsPerDay(minDate, maxDate).first()
        else {
            repository.getTransactionAmountsPerDayForAccount(minDate, maxDate, account.id)
                .first()
        }

        val result = mutableListOf<Any>()

        if (amountsPerDay.isNotEmpty()) {
            var index = 0
            for (transaction in transactions) {
                result.add(transaction)
                if (transaction.date != amountsPerDay[index].transactionDate) {
                    result.add(result.size - 1, amountsPerDay[index])
                    index++
                }
            }
            if (index < amountsPerDay.size)
                result.add(amountsPerDay[index])
        }
        return result.reversed()
    }
}
