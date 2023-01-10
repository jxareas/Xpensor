package com.jxareas.xpensor.features.accounts.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UpdateAccountUseCase @Inject constructor(private val repository: AccountRepository) {

    suspend operator fun invoke(account: Account) =
        repository.updateAccount(account)
}
