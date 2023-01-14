package com.jxareas.xpensor.features.converter.data.remote.api

import com.jxareas.xpensor.BuildConfig
import com.jxareas.xpensor.features.converter.data.remote.api.constants.ApiEndpoints
import com.jxareas.xpensor.features.converter.data.remote.api.constants.ApiParams
import com.jxareas.xpensor.features.converter.data.remote.dto.CurrencyConversionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeCurrencyApi {

    companion object {
        const val CURRENCY_API_BASE_URL = "https://freecurrencyapi.net/"
        const val CURRENCY_KEY = BuildConfig.CURRENCY_API_KEY
    }

    @GET(ApiEndpoints.LATEST)
    suspend fun getCurrencyRates(
        @Query(ApiParams.BASE_CURRENCY) base: String,
    ): Response<CurrencyConversionDto>
}
