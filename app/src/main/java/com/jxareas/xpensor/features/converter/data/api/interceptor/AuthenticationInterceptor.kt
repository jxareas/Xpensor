package com.jxareas.xpensor.features.converter.data.api.interceptor

import com.jxareas.xpensor.features.converter.data.api.constants.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
object AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("apikey", ApiConstants.CURRENCY_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
