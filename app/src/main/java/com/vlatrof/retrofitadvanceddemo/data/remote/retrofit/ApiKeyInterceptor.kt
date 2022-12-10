package com.vlatrof.retrofitadvanceddemo.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(name = "appid", value = apiKey)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(url = newUrl)
            .build()

        return chain.proceed(request = newRequest)
    }
}
