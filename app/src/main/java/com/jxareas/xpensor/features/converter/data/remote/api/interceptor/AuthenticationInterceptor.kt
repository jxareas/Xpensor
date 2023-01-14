package com.jxareas.xpensor.features.converter.data.remote.api.interceptor

import com.jxareas.xpensor.features.converter.data.remote.api.FreeCurrencyApi
import com.jxareas.xpensor.features.converter.data.remote.api.constants.ApiParams
import okhttp3.Interceptor
import okhttp3.Response

object AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter(ApiParams.API_KEY, FreeCurrencyApi.CURRENCY_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
