package com.jxareas.xpensor.features.converter.data.remote.source.impl

import com.jxareas.xpensor.features.converter.data.remote.api.FreeCurrencyApi
import com.jxareas.xpensor.features.converter.data.remote.dto.CurrencyConversionDto
import com.jxareas.xpensor.features.converter.data.remote.source.CurrencyRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class FreeCurrencyApiRemoteDataSource @Inject constructor(
    private val freeCurrencyApi: FreeCurrencyApi,
) : CurrencyRemoteDataSource {

    override suspend fun getAvailableCurrencyRates(baseCurrency: String): Response<CurrencyConversionDto> =
        freeCurrencyApi.getCurrencyRates(baseCurrency)

}
