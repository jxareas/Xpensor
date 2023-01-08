package com.jxareas.xpensor.features.converter.data.repository

import android.content.Context
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.Resource
import com.jxareas.xpensor.features.converter.data.api.ConverterApi
import com.jxareas.xpensor.features.converter.data.mapper.toDomain
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison
import com.jxareas.xpensor.features.converter.domain.repository.ConverterRepository
import java.io.IOException
import javax.inject.Inject

class DefaultConverterRepository @Inject constructor(
    private val context: Context,
    private val converterApi: ConverterApi,
) : ConverterRepository {

    override suspend fun getCurrencyRates(base: String): Resource<CurrencyComparison> =
        try {
            val response = converterApi.getCurrencyRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.Success(result.toDomain())
            else Resource.Error(response.message())
        } catch (exception: IOException) {
            Resource.Error(context.getString(R.string.connection_error))
        }
}
