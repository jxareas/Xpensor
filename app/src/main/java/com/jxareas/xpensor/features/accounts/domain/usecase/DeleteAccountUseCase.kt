package com.jxareas.xpensor.features.accounts.domain.usecase

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DeleteAccountUseCase @Inject constructor(private val repository: AccountRepository) {

    suspend operator fun invoke(account: AccountWithDetails) =
        repository.deleteAccount(account)

}