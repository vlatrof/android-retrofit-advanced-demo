package com.vlatrof.retrofitadvanceddemo.data.remote.geocoordinates

import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.GeoCoordinates
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherApi
import java.net.UnknownHostException
import javax.inject.Inject

class GeoCoordinatesRemoteDataSource @Inject constructor(

    private val weatherApi: WeatherApi
    
) {

    sealed class Result {
        class Success(val data: List<GeoCoordinates>) : Result()
        object UnknownHostError : Result()
        object NotFoundError : Result()
    }

    suspend fun getGeoCoordinates(cityName: String): Result {
        try {
            val response = weatherApi.getGeoCoordinates(cityName = cityName)
            if (response.body()!!.isEmpty()) {
                return Result.NotFoundError
            }
            return Result.Success(data = response.body()!!)
        } catch (exception: UnknownHostException) {
            return Result.UnknownHostError
        }
    }
}
