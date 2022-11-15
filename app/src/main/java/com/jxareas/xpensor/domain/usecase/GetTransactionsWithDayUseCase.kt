package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.model.TransactionWithDetails
import com.jxareas.xpensor.domain.repository.TransactionRepository
import com.jxareas.xpensor.utils.DateRange
import com.jxareas.xpensor.utils.DateUtils
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import javax.inject.Inject

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
            repository.getTransactionAmountsPerDayForAccount(minDate, maxDate, account.id ?: 0)
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