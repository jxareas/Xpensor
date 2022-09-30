package com.jxareas.xpensor.domain.repository

import com.jxareas.xpensor.data.api.dto.CurrencyResponse
import com.jxareas.xpensor.utils.Resource

interface ConverterRepository {
    suspend fun getCurrencyRates(base: String): Resource<CurrencyResponse>
}