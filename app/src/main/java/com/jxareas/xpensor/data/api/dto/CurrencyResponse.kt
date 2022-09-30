package com.jxareas.xpensor.data.api.dto

import com.squareup.moshi.Json

data class CurrencyResponse(
    @Json(name = "data") val rates: RatesResponse,
    val query: Query,
)