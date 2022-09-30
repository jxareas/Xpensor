package com.jxareas.xpensor.data.api.dto

import com.squareup.moshi.Json

data class Query(
    @Json(name = "base_currency")
    val base: String,
)