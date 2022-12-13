package com.vlatrof.retrofitadvanceddemo.data.remote.geocoordinates

import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherApi
import javax.inject.Inject

class GeoCoordinatesRemoteDataSource @Inject constructor(

    private val weatherApi: WeatherApi
    
) {
    
    suspend fun getGeoCoordinates(cityName: String) =
        weatherApi.getGeoCoordinates(cityName = cityName).body()
}
