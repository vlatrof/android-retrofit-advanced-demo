package com.vlatrof.retrofitadvanceddemo.data.remote.retrofit

import com.vlatrof.retrofitadvanceddemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(name = "appid", value = BuildConfig.OPEN_WEATHER_API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(url = newUrl)
            .build()

        return chain.proceed(request = newRequest)
    }
}
