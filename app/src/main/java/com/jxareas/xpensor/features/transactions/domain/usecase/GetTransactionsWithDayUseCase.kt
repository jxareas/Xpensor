package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.common.utils.DateRange
import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@ViewModelScoped
class GetTransactionsWithDayUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {

    suspend fun invoke(
        transactions: List<TransactionDetails>,
        dateRange: DateRange,
        account: Account?,
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
            for (details in transactions) {
                result.add(details)
                if (details.transaction.date != amountsPerDay[index].date) {
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
