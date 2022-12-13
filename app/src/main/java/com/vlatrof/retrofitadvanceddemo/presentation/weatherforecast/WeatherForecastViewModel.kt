package com.vlatrof.retrofitadvanceddemo.presentation.weatherforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.data.di.MainDispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherForecast
import com.vlatrof.retrofitadvanceddemo.domain.GetGeoCoordinatesUseCase
import com.vlatrof.retrofitadvanceddemo.domain.GetWeatherForecastUseCase
import com.vlatrof.retrofitadvanceddemo.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(

    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getGeoCoordinatesUseCase: GetGeoCoordinatesUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    savedStateHandle: SavedStateHandle

) : BaseViewModel() {

    private val mutableWeatherForecastState = MutableLiveData<ResourceState<WeatherForecast>>(
        ResourceState.Initial
    )
    val weatherForecastLiveData: LiveData<ResourceState<WeatherForecast>> =
        mutableWeatherForecastState

    init {
        fetchWeatherForecast(
            cityName = WeatherForecastFragmentArgs
                .fromSavedStateHandle(savedStateHandle = savedStateHandle)
                .cityName,
            units = "metric"
        )
    }

    private fun fetchWeatherForecast(cityName: String, units: String) = viewModelScope.launch(
        mainDispatcher
    ) {
        mutableWeatherForecastState.value = ResourceState.Loading

        try {
            val coordinatesResponse = getGeoCoordinatesUseCase(cityName = cityName)
            if (coordinatesResponse.isNullOrEmpty()) {
                mutableWeatherForecastState.value = ResourceState.Error(
                    resourceMessageId = R.string.no_such_city_was_found_error
                )
                return@launch
            }

            val firstMatch = coordinatesResponse[0]
            val weatherForecastResponse = getWeatherForecastUseCase(
                lat = firstMatch.lat,
                lon = firstMatch.lon,
                units = units
            )
            if (weatherForecastResponse == null) {
                mutableWeatherForecastState.value = ResourceState.Error(
                    resourceMessageId = R.string.no_such_city_was_found_error
                )
                return@launch
            }
            mutableWeatherForecastState.value = ResourceState.Success(
                data = weatherForecastResponse
            )
        } catch (exception: UnknownHostException) {
            mutableWeatherForecastState.value = ResourceState.Error(
                resourceMessageId = R.string.internet_connection_error
            )
        }
    }
}
