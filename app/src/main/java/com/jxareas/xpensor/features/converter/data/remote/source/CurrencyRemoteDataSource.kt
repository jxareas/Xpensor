package com.jxareas.xpensor.features.converter.data.remote.source

import com.jxareas.xpensor.features.converter.data.remote.dto.CurrencyConversionDto
import retrofit2.Response

interface CurrencyRemoteDataSource {
    suspend fun getAvailableCurrencyRates(baseCurrency: String): Response<CurrencyConversionDto>
}
