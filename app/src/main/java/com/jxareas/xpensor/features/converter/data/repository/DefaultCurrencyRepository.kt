package com.jxareas.xpensor.features.converter.data.repository

import android.content.Context
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.Resource
import com.jxareas.xpensor.features.converter.data.mapper.toCurrencyComparison
import com.jxareas.xpensor.features.converter.data.remote.api.FreeCurrencyApi
import com.jxareas.xpensor.features.converter.domain.model.BaseCurrency
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison
import com.jxareas.xpensor.features.converter.domain.repository.CurrencyRepository
import java.io.IOException
import javax.inject.Inject

class DefaultCurrencyRepository @Inject constructor(
    private val context: Context,
    private val converterApi: FreeCurrencyApi,
) : CurrencyRepository {

    override suspend fun getCurrencyRates(baseCurrency: BaseCurrency): Resource<CurrencyComparison> =
        try {
            val response = converterApi.getCurrencyRates(baseCurrency.name)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.Success(result.toCurrencyComparison())
            else Resource.Error(response.message())
        } catch (exception: IOException) {
            Resource.Error(context.getString(R.string.connection_error))
        }
}
