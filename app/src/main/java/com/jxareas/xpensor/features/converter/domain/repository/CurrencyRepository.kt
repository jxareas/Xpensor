package com.jxareas.xpensor.features.converter.domain.repository

import com.jxareas.xpensor.common.utils.Resource
import com.jxareas.xpensor.features.converter.domain.model.BaseCurrency
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison

interface CurrencyRepository {
    suspend fun getCurrencyRates(baseCurrency: BaseCurrency): Resource<CurrencyComparison>
}
