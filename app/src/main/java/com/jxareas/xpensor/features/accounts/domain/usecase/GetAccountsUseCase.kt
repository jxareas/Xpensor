package com.jxareas.xpensor.features.accounts.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@ViewModelScoped
class GetAccountsUseCase @Inject constructor(private val repository: AccountRepository) {

    operator fun invoke(): Flow<List<AccountWithDetails>> =
        repository.getAccounts()
}
