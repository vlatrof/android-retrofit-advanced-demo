package com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.models

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastStamp>
)

data class ForecastStamp(
    val dt: Long,
    @SerializedName("main")
    val weather: Weather,
    val wind: Wind
)

data class Weather(
    val temp: Double,
    @SerializedName("feels_like")
    val tempFeelsLike: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double
)
