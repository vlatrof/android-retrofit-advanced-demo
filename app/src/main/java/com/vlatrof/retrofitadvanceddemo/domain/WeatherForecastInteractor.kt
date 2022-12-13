package com.vlatrof.retrofitadvanceddemo.domain

import com.vlatrof.retrofitadvanceddemo.data.di.IODispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherForecast
import com.vlatrof.retrofitadvanceddemo.data.remote.weatherforecast.WeatherForecastRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// TODO: TO INTERACTOR

class WeatherForecastInteractor @Inject constructor(

    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource

) {

    suspend fun getWeatherForecast(lat: Double, lon: Double, units: String): WeatherForecast? =
        withContext(ioDispatcher) {
            weatherForecastRemoteDataSource.getWeatherForecast(lat = lat, lon = lon, units = units)
        }
}
