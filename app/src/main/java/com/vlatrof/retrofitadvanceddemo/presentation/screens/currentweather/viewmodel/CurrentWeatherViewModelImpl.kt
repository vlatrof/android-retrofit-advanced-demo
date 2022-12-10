package com.vlatrof.retrofitadvanceddemo.presentation.screens.currentweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSource
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.CurrentWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentWeatherViewModelImpl(

    private val cityName: String,
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    ) : CurrentWeatherViewModel() {

    override val currentWeatherLiveData = MutableLiveData<CurrentWeather>()

    override fun fetchCurrentWeather() {
        viewModelScope.launch(mainDispatcher) {
            val geoCoordinatesResponse =
                weatherRemoteDataSource.getGeoCoordinates(cityName = cityName)
            if (!geoCoordinatesResponse.isSuccessful) {
                return@launch
            }
            if (geoCoordinatesResponse.body()?.isEmpty()!!) {
                return@launch
            }

            val geoCoordinates = geoCoordinatesResponse.body()?.get(0)
            val currentWeatherResponse =
                weatherRemoteDataSource.getCurrentWeather(
                    lat = geoCoordinates!!.lat,
                    lon = geoCoordinates.lon,
                    units = "metric"
                )
            if (currentWeatherResponse.isSuccessful) {
                currentWeatherLiveData.value = currentWeatherResponse.body()
            }
        }
    }
}
