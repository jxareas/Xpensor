package com.jxareas.xpensor.features.converter.data.api.dto

import com.squareup.moshi.Json

data class CurrencyDto(
    @Json(name = "data") val rates: CurrencyRatesDto,
    val baseCurrency: BaseCurrencyDto,
)
