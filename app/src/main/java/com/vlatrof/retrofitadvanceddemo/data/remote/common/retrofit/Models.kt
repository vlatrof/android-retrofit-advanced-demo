package com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit

data class GeoCoordinates(
    val name: String,
    val lat: Double,
    val lon: Double
)

data class CurrentWeather(
    val name: String,
    val sys: Sys,
    val cod: String,
    val dt: Long,
    val timezone: Int,
    val main: Main,
    val wind: Wind
)

data class Sys(
    val country: String
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
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double
)

data class City(
    val timezone: Int
)
