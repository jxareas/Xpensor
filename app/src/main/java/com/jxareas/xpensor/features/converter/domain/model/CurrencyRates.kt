package com.jxareas.xpensor.features.converter.domain.model

import com.jxareas.xpensor.core.domain.model.Domain

data class CurrencyRates(
    val usd: Currency,
    val eur: Currency,
    val nio: Currency,
    val crc: Currency,
    val gbp: Currency,
) : Domain
