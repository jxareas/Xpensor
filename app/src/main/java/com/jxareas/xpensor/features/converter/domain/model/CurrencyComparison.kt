package com.jxareas.xpensor.features.converter.domain.model

import com.jxareas.xpensor.core.domain.model.Domain

data class CurrencyComparison(
    val baseCurrency: BaseCurrency,
    val currencyRates: CurrencyRates,
) : Domain
