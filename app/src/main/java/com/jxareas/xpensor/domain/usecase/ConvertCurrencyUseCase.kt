package com.jxareas.xpensor.domain.usecase

import com.jxareas.xpensor.domain.repository.ConverterRepository
import com.jxareas.xpensor.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ConvertCurrencyUseCase @Inject constructor(private val repository: ConverterRepository) {

    suspend operator fun invoke(amount: Double, from: String, to: String): Double =
        if(from == to)
            amount
        else getCurrencyRate(amount, from, to)

    private suspend fun getCurrencyRate(amount: Double, from: String, to: String): Double {
        when (val response = repository.getCurrencyRates(from)) {
            is Resource.Success -> {
                val rates = response.data?.rates ?: return -1.0
                val rate = when (to) {
                    "USD" -> rates.usd
                    "EUR" -> rates.eur
                    "RUB" -> rates.rub
                    "UAH" -> rates.uah
                    "PLN" -> rates.pln
                    else -> return -1.0
                }
                return amount * rate
            }
            is Resource.Error -> return -2.0
        }
    }




}