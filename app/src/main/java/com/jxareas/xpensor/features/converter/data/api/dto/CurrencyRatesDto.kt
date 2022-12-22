package com.jxareas.xpensor.features.converter.data.api.dto

import com.squareup.moshi.Json

data class CurrencyRatesDto(
    @Json(name = "USD") val usd: Double = 1.0,
    @Json(name = "EUR") val eur: Double = 1.0,
    @Json(name = "NIO") val nio: Double = 1.0,
    @Json(name = "CRC") val crc: Double = 1.0,
    @Json(name = "GBP") val gbp: Double = 1.0,
)
