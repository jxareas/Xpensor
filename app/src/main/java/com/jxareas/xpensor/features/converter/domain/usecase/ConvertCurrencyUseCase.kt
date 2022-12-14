package com.jxareas.xpensor.features.converter.domain.usecase

import com.jxareas.xpensor.common.utils.Resource
import com.jxareas.xpensor.features.converter.domain.model.Currencies
import com.jxareas.xpensor.features.converter.domain.repository.ConverterRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ConvertCurrencyUseCase @Inject constructor(private val repository: ConverterRepository) {

    companion object {
        const val UNEXPECTED_ERROR = -1.0
        const val NO_INTERNET_CONNECTION = -2.0
    }

    suspend operator fun invoke(amount: Double, from: String, to: String): Double =
        if (from == to)
            amount
        else getCurrencyRate(amount, from, to)

    private suspend fun getCurrencyRate(amount: Double, from: String, to: String): Double =
        when (val response = repository.getCurrencyRates(from)) {
            is Resource.Success -> {
                val rates = response.data?.currencyRates
                if (rates != null) {
                    val currencyRate = when (to) {
                        Currencies.USD.name -> rates.usd
                        Currencies.EUR.name -> rates.eur
                        Currencies.NIO.name -> rates.nio
                        Currencies.CRC.name -> rates.crc
                        Currencies.GBP.name -> rates.gbp
                        else -> rates.usd
                    }
                    amount * currencyRate.rate
                } else UNEXPECTED_ERROR
            }
            is Resource.Error -> NO_INTERNET_CONNECTION
        }
}
