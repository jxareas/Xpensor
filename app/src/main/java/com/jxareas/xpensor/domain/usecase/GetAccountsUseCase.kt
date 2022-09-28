package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@ViewModelScoped
class GetAccountsUseCase(private val repository: AccountRepository) {
    operator fun invoke(): Flow<List<Account>> {
        return repository.getAccounts()
    }
}