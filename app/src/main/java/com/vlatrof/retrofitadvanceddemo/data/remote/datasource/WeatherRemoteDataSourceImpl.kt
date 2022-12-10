package com.vlatrof.retrofitadvanceddemo.data.remote.datasource

import com.vlatrof.retrofitadvanceddemo.data.di.IODispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.WeatherApi
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRemoteDataSourceImpl @Inject constructor(

    @IODispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val weatherApi: WeatherApi

) : WeatherRemoteDataSource {

    override suspend fun getGeoCoordinates(cityName: String) = withContext(ioDispatcher) {
        weatherApi.getGeoCoordinates(cityName = cityName)
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double, units: String) =
        withContext(ioDispatcher) {
            weatherApi.getCurrentWeather(
                lat = lat,
                lon = lon,
                units = units
            )
        }

    override suspend fun getWeatherForecast(lat: Double, lon: Double, units: String) =
        withContext(ioDispatcher) {
            weatherApi.getWeatherForecast(
                lat = lat,
                lon = lon,
                units = units
            )
        }
}
