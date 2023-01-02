package com.jxareas.xpensor.features.converter.domain.usecase

import com.jxareas.xpensor.features.converter.domain.repository.CurrencyRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetPreferredCurrencyNameUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    operator fun invoke(): String =
        currencyRepository.getPreferredCurrencyName()

}