package com.vlatrof.retrofitadvanceddemo.presentation.screens.currentweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSource

class CurrentWeatherViewModelFactory(

    private val cityName: String,
    private val weatherRemoteDataSource: WeatherRemoteDataSource

    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModelImpl(
            cityName = cityName,
            weatherRemoteDataSource = weatherRemoteDataSource
        ) as T
    }
}
