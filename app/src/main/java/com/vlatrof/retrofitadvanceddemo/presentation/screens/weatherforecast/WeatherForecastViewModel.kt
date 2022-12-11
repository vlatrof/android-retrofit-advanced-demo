package com.vlatrof.retrofitadvanceddemo.presentation.screens.weatherforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vlatrof.retrofitadvanceddemo.data.di.MainDispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSource
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.WeatherForecast
import com.vlatrof.retrofitadvanceddemo.presentation.screens.currentweather.CurrentWeatherFragmentArgs
import com.vlatrof.retrofitadvanceddemo.presentation.screens.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(

    @MainDispatcher private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    savedStateHandle: SavedStateHandle

) : BaseViewModel() {

    private val cityNameArg: String = CurrentWeatherFragmentArgs
        .fromSavedStateHandle(savedStateHandle = savedStateHandle).cityName

    private val mutableWeatherForecastLiveData =
        MutableLiveData<ResourceState<WeatherForecast>>(ResourceState.Initial)
    val weatherForecastLiveData: LiveData<ResourceState<WeatherForecast>> =
        mutableWeatherForecastLiveData

    init {
        fetchWeatherForecast()
    }

    private fun fetchWeatherForecast() = viewModelScope.launch(mainDispatcher) {
        mutableWeatherForecastLiveData.value = ResourceState.Loading

        val geoCoordinatesResponse = weatherRemoteDataSource
            .getGeoCoordinates(cityName = cityNameArg)
        if (!geoCoordinatesResponse.isSuccessful) return@launch
        if (geoCoordinatesResponse.body()?.isEmpty()!!) return@launch
        val geoCoordinates = geoCoordinatesResponse.body()?.get(0)

        val weatherForecastResponse = weatherRemoteDataSource.getWeatherForecast(
            lat = geoCoordinates!!.lat,
            lon = geoCoordinates.lon,
            units = "metric"
        )
        if (weatherForecastResponse.isSuccessful) {
            mutableWeatherForecastLiveData.value =
                ResourceState.Success(data = weatherForecastResponse.body()!!)
        }
    }
}
