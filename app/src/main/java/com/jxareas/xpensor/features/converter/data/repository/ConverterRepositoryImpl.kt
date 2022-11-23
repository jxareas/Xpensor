package com.jxareas.xpensor.features.converter.data.repository

import android.content.Context
import com.jxareas.xpensor.R
import com.jxareas.xpensor.features.converter.data.api.ConverterApi
import com.jxareas.xpensor.features.converter.data.api.dto.CurrencyResponse
import com.jxareas.xpensor.features.converter.domain.repository.ConverterRepository
import com.jxareas.xpensor.common.utils.Resource
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(
    private val context: Context,
    private val converterApi: ConverterApi,
) :
    ConverterRepository {
    override suspend fun getCurrencyRates(base: String): Resource<CurrencyResponse> =
        try {
            val response = converterApi.getCurrencyRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.Success(result)
            else Resource.Error(response.message())
        } catch (exception: Exception) {
            Resource.Error(context.getString(R.string.connection_error))
        }

}