package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.domain.model.Account
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
        account: Account?,
    ): Flow<List<TransactionView>> {

        val minDate = dateRange.first ?: DateUtils.DEFAULT_LOCAL_DATE
        val maxDate = dateRange.second ?: DateUtils.getCurrentLocalDate()

        return if (account == null)
            repository.getTransactionViews(minDate, maxDate)
        else repository.getTransactionViewsFromAccount(minDate, maxDate, account.id)

    }

}