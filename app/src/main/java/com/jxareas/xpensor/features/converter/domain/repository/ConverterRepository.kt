package com.jxareas.xpensor.features.converter.domain.repository

import com.jxareas.xpensor.common.utils.Resource
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison

interface ConverterRepository {
    suspend fun getCurrencyRates(base: String): Resource<CurrencyComparison>
}