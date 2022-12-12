package com.vlatrof.retrofitadvanceddemo.data.remote.weatherforecast

import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherApi
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherForecast
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherForecastRemoteDataSource @Inject constructor(

    private val weatherApi: WeatherApi

) {

    sealed class Result {
        class Success(val data: WeatherForecast) : Result()
        object UnknownHostError : Result()
        object WrongRequestDataError : Result()
    }

    suspend fun getWeatherForecast(lat: Double, lon: Double, units: String): Result {
        try {
            val response = weatherApi.getWeatherForecast(lat = lat, lon = lon, units = units)
            if (!response.isSuccessful) {
                return Result.WrongRequestDataError
            }
            return Result.Success(data = response.body()!!)
        } catch (exception: UnknownHostException) {
            return Result.UnknownHostError
        }
    }
}
