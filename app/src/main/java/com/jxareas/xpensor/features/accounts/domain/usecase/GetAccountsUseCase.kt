package com.jxareas.xpensor.features.accounts.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAccountsUseCase @Inject constructor(private val repository: AccountRepository) {

    fun invoke(): Flow<List<Account>> =
        repository.getAccounts()
}
