package com.jxareas.xpensor.features.converter.data.api.dto

import com.squareup.moshi.Json

data class BaseCurrencyDto(
    @Json(name = "base_currency")
    val base: String,
)
