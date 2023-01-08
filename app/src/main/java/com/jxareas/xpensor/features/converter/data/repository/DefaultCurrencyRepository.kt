package com.jxareas.xpensor.features.converter.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.common.utils.Resource
import com.jxareas.xpensor.features.converter.data.api.ConverterApi
import com.jxareas.xpensor.features.converter.data.mapper.asCurrencyComparison
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison
import com.jxareas.xpensor.features.converter.domain.repository.CurrencyRepository
import java.io.IOException
import javax.inject.Inject

class DefaultCurrencyRepository @Inject constructor(
    private val context: Context,
    private val converterApi: ConverterApi,
    private val preferences: SharedPreferences,
) : CurrencyRepository {

    override suspend fun getCurrencyRates(base: String): Resource<CurrencyComparison> =
        try {
            val response = converterApi.getCurrencyRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.Success(result.asCurrencyComparison())
            else Resource.Error(response.message())
        } catch (exception: IOException) {
            Resource.Error(context.getString(R.string.connection_error))
        }

    override fun getPreferredCurrencyName(): String =
        preferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY) ?: MAIN_CURRENCY


}
