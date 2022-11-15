package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.model.TransactionWithDetails
import com.jxareas.xpensor.domain.repository.TransactionRepository
import com.jxareas.xpensor.utils.DateRange
import com.jxareas.xpensor.utils.DateUtils
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
            repository.getTransactionViews(minDate, maxDate)
        else repository.getTransactionViewsFromAccount(minDate, maxDate, account.id)

    }

}