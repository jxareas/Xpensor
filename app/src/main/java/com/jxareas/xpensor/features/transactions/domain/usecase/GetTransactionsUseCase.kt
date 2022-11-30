package com.jxareas.xpensor.features.transactions.domain.usecase

import com.jxareas.xpensor.common.utils.DateRange
import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {

    operator fun invoke(
        dateRange: DateRange,
        account: AccountWithDetails?,
    ): Flow<List<TransactionWithDetails>> {

        val minDate = dateRange.first ?: DateUtils.DEFAULT_LOCAL_DATE
        val maxDate = dateRange.second ?: DateUtils.getCurrentLocalDate()

        return if (account == null)
            repository.getTransactionDetails(minDate, maxDate)
        else repository.getTransactionDetailsFromAccount(minDate, maxDate, account.id)

    }

}