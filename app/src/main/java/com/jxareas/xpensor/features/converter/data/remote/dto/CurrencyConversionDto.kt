package com.jxareas.xpensor.features.converter.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class CurrencyConversionDto(
    @Json(name = "data") val ratesDto: CurrencyRatesDto,
    @Json(name = "query") val baseCurrencyDto: BaseCurrencyDto,
)
