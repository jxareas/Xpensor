package com.jxareas.xpensor.features.converter.data.api

import com.jxareas.xpensor.features.converter.data.api.dto.CurrencyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterApi {


    @GET("api/v2/latest")
    suspend fun getCurrencyRates(
        @Query("base_currency") base: String,
    ): Response<CurrencyDto>

}