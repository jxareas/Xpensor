package com.jxareas.xpensor.data.api.dto

import com.squareup.moshi.Json

data class RatesResponse(
    @Json(name = "USD") val usd: Double = 1.0,
    @Json(name = "EUR") val eur: Double = 1.0,
    @Json(name = "RUB") val rub: Double = 1.0,
    @Json(name = "UAH") val uah: Double = 1.0,
    @Json(name = "PLN") val pln: Double = 1.0,
)