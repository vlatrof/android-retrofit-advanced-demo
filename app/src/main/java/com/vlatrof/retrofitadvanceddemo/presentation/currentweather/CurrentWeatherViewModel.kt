package com.vlatrof.retrofitadvanceddemo.presentation.currentweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vlatrof.retrofitadvanceddemo.data.di.MainDispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.datasource.WeatherRemoteDataSource
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.CurrentWeather
import com.vlatrof.retrofitadvanceddemo.presentation.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(

    @MainDispatcher private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    savedStateHandle: SavedStateHandle

) : BaseViewModel() {

    private val cityNameArg: String = CurrentWeatherFragmentArgs
        .fromSavedStateHandle(savedStateHandle = savedStateHandle).cityName

    private val mutableCurrentWeatherLiveData =
        MutableLiveData<ResourceState<CurrentWeather>>(ResourceState.Initial)
    val currentWeatherLiveData: LiveData<ResourceState<CurrentWeather>> =
        mutableCurrentWeatherLiveData

    init {
        fetchCurrentWeather()
    }

    private fun fetchCurrentWeather() = viewModelScope.launch(mainDispatcher) {
        mutableCurrentWeatherLiveData.value = ResourceState.Loading

        val geoCoordinatesResponse = weatherRemoteDataSource
            .getGeoCoordinates(cityName = cityNameArg)
        if (!geoCoordinatesResponse.isSuccessful) return@launch
        if (geoCoordinatesResponse.body()?.isEmpty()!!) return@launch
        val geoCoordinates = geoCoordinatesResponse.body()?.get(0)

        val currentWeatherResponse = weatherRemoteDataSource.getCurrentWeather(
            lat = geoCoordinates!!.lat,
            lon = geoCoordinates.lon,
            units = "metric"
        )
        if (currentWeatherResponse.isSuccessful) {
            mutableCurrentWeatherLiveData.value =
                ResourceState.Success(data = currentWeatherResponse.body()!!)
        }
    }
}
