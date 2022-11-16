package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.model.CategoryWithDetails
import com.jxareas.xpensor.domain.repository.CategoryRepository
import com.jxareas.xpensor.utils.DateRange
import com.jxareas.xpensor.utils.DateUtils
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository,
) {

    operator fun invoke(dateRange: DateRange, account: AccountWithDetails?):
            Flow<List<CategoryWithDetails>> {

        val minDate = dateRange.first ?: DateUtils.DEFAULT_LOCAL_DATE
        val maxDate = dateRange.second ?: DateUtils.getCurrentLocalDate()

        return if (account == null)
            repository.getCategoryViews(minDate, maxDate)
        else repository.getCategoryViewsFromAccount(minDate, maxDate, account.id)

    }


}