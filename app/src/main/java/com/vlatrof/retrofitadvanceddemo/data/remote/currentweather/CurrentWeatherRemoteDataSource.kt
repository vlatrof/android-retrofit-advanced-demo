package com.vlatrof.retrofitadvanceddemo.data.remote.currentweather

import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.CurrentWeather
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherApi
import java.net.UnknownHostException
import javax.inject.Inject

class CurrentWeatherRemoteDataSource @Inject constructor(
    
    private val weatherApi: WeatherApi

) {

    sealed class Result {
        class Success(val data: CurrentWeather) : Result()
        object UnknownHostError : Result()
        object WrongRequestDataError : Result()
    }

    suspend fun getCurrentWeather(lat: Double, lon: Double, units: String): Result {
        try {
            val response = weatherApi.getCurrentWeather(lat = lat, lon = lon, units = units)
            if (!response.isSuccessful) {
                return Result.WrongRequestDataError
            }
            return Result.Success(data = response.body()!!)
        } catch (exception: UnknownHostException) {
            return Result.UnknownHostError
        }
    }
}
