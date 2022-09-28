package com.jxareas.xpensor.ui.accounts

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.domain.usecase.GetAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
) : ViewModel() {



}