package com.vlatrof.retrofitadvanceddemo.data.remote.datasource

import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.CurrentWeather
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.GeoCoordinates
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.WeatherForecast
import retrofit2.Response

interface WeatherRemoteDataSource {

    suspend fun getGeoCoordinates(cityName: String): Response<List<GeoCoordinates>>

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String
    ): Response<CurrentWeather>

    suspend fun getWeatherForecast(
        lat: Double,
        lon: Double,
        units: String
    ): Response<WeatherForecast>
}
