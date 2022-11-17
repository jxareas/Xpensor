package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAccountsUseCase @Inject constructor(private val repository: AccountRepository) {
    operator fun invoke(): Flow<List<AccountWithDetails>> =
        repository.getAccounts()
}