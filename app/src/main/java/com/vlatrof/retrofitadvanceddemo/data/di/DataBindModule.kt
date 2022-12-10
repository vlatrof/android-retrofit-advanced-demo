package com.vlatrof.retrofitadvanceddemo.data.di

import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSource
import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRemoteDataSource(
        implementation: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource
}
