package com.vlatrof.retrofitadvanceddemo.data.remote.weatherforecast

import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherApi
import javax.inject.Inject

class WeatherForecastRemoteDataSource @Inject constructor(

    private val weatherApi: WeatherApi

) {

    suspend fun getWeatherForecast(lat: Double, lon: Double, units: String) =
        weatherApi.getWeatherForecast(lat = lat, lon = lon, units = units).body()
}
