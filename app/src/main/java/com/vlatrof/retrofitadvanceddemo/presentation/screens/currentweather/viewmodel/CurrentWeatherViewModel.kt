package com.vlatrof.retrofitadvanceddemo.presentation.screens.currentweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.CurrentWeather

abstract class CurrentWeatherViewModel : ViewModel() {

    abstract val currentWeatherLiveData: LiveData<CurrentWeather>

    abstract fun fetchCurrentWeather()
}
