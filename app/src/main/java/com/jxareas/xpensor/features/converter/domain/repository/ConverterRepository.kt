package com.jxareas.xpensor.features.converter.domain.repository

import com.jxareas.xpensor.features.converter.data.api.dto.CurrencyResponse
import com.jxareas.xpensor.common.utils.Resource

interface ConverterRepository {
    suspend fun getCurrencyRates(base: String): Resource<CurrencyResponse>
}