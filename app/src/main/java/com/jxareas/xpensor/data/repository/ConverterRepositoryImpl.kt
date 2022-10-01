package com.jxareas.xpensor.data.repository

import android.content.Context
import com.jxareas.xpensor.R
import com.jxareas.xpensor.data.api.dto.CurrencyResponse
import com.jxareas.xpensor.data.api.service.ConverterService
import com.jxareas.xpensor.domain.repository.ConverterRepository
import com.jxareas.xpensor.utils.Resource
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(
    private val context: Context,
    private val converterService: ConverterService,
) :
    ConverterRepository {
    override suspend fun getCurrencyRates(base: String): Resource<CurrencyResponse> =
        try {
            val response = converterService.getCurrencyRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.Success(result)
            else Resource.Error(response.message())
        } catch (exception: Exception) {
            Resource.Error(context.getString(R.string.connection_error))
        }

}