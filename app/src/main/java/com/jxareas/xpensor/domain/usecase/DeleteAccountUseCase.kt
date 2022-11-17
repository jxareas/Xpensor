package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.repository.AccountRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DeleteAccountUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(account: AccountWithDetails) =
        repository.deleteAccount(account)
}