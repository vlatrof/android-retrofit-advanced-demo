package com.vlatrof.retrofitadvanceddemo.data.di

import com.vlatrof.retrofitadvanceddemo.BuildConfig
import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSource
import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSourceImpl
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.ApiKeyInterceptor
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.WeatherApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @BaseUrl
    @Provides
    @Singleton
    fun provideBaseURL(): String = BuildConfig.BASE_URL

    @ApiKey
    @Provides
    @Singleton
    fun provideOpenWeatherApiKey(): String = BuildConfig.OPEN_WEATHER_API_KEY

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApiKey apiKey: String): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey = apiKey))
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            )
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRemoteDataSource(
        implementation: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource
}
