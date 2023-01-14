package com.jxareas.xpensor.features.converter.data.remote.dto

import com.squareup.moshi.Json

data class BaseCurrencyDto(
    @Json(name = "base_currency")
    val currencyName: String,
)
