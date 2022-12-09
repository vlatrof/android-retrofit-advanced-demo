package com.vlatrof.retrofitadvanceddemo.data.remote.retrofit

import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.models.GeoCoordinates
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.models.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("geo/1.0/direct")
    suspend fun getCoordinates(
        @Query("q") cityName: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): Response<List<GeoCoordinates>>

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<WeatherForecast>
}
