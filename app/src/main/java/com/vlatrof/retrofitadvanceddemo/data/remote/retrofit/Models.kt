package com.vlatrof.retrofitadvanceddemo.data.remote.retrofit

import com.google.gson.annotations.SerializedName

data class GeoCoordinates(
    val name: String,
    val lat: Double,
    val lon: Double
)

data class CurrentWeather(
    val cod: String,
    val dt: Long,
    val timezone: Int,
    val main: Main,
    val wind: Wind
)

data class WeatherForecast(
    val cod: String,
    val message: Int,
    val list: List<WeatherStamp>,
    val city: City
)

data class WeatherStamp(
    val dt: Long,
    val main: Main,
    val wind: Wind
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double
)

data class City(
    val timezone: Int
)
