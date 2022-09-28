package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class UpdateAccountUseCase(private val repository: AccountRepository) {
    suspend operator fun invoke(account: Account) {
        repository.updateAccount(account)
    }
}