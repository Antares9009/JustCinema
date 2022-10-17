package com.antares.justcinema.network

import com.antares.justcinema.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.security.Key

class MovieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOrigin = chain.request()
        val urlOrigin = requestOrigin.url

        val url = urlOrigin.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        val requestBuilder = requestOrigin.newBuilder().url(url)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }

}