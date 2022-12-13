package com.vlatrof.retrofitadvanceddemo.data.remote.currentweather

import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherApi
import javax.inject.Inject

class CurrentWeatherRemoteDataSource @Inject constructor(
    
    private val weatherApi: WeatherApi

) {
    
    suspend fun getCurrentWeather(lat: Double, lon: Double, units: String) =
        weatherApi.getCurrentWeather(lat = lat, lon = lon, units = units).body()
}
