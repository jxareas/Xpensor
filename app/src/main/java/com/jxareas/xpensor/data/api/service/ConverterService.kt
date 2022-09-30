package com.jxareas.xpensor.data.api.service

import com.jxareas.xpensor.data.api.dto.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterService {


    @GET("api/v2/latest")
    suspend fun getCurrencyRates(
        @Query("base_currency") base: String,
    ): Response<CurrencyResponse>

}