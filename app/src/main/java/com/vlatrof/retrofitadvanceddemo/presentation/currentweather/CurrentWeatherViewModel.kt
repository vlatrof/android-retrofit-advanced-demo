package com.vlatrof.retrofitadvanceddemo.presentation.currentweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.data.di.MainDispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.CurrentWeather
import com.vlatrof.retrofitadvanceddemo.domain.GetCurrentWeatherUseCase
import com.vlatrof.retrofitadvanceddemo.domain.GetGeoCoordinatesUseCase
import com.vlatrof.retrofitadvanceddemo.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(

    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getGeoCoordinatesUseCase: GetGeoCoordinatesUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    savedStateHandle: SavedStateHandle

) : BaseViewModel() {
    
    private val mutableCurrentWeatherState = MutableLiveData<ResourceState<CurrentWeather>>(
        ResourceState.Initial
    )
    val currentWeatherState: LiveData<ResourceState<CurrentWeather>> =
        mutableCurrentWeatherState

    init {
        fetchCurrentWeather(
            cityName = CurrentWeatherFragmentArgs
                .fromSavedStateHandle(savedStateHandle = savedStateHandle)
                .cityName,
            units = "metric"
        )
    }

    private fun fetchCurrentWeather(cityName: String, units: String) = viewModelScope.launch(
        mainDispatcher
    ) {
        mutableCurrentWeatherState.value = ResourceState.Loading
        
        try {
            val coordinatesResponse = getGeoCoordinatesUseCase(cityName = cityName)
            if (coordinatesResponse.isNullOrEmpty()) {
                mutableCurrentWeatherState.value = ResourceState.Error(
                    resourceMessageId = R.string.no_such_city_was_found_error
                )
                return@launch
            }

            val firstMatch = coordinatesResponse[0]
            val currentWeatherResponse = getCurrentWeatherUseCase(
                lat = firstMatch.lat,
                lon = firstMatch.lon,
                units = units
            )
            if (currentWeatherResponse == null) {
                mutableCurrentWeatherState.value = ResourceState.Error(
                    resourceMessageId = R.string.no_such_city_was_found_error
                )
                return@launch
            }
            mutableCurrentWeatherState.value = ResourceState.Success(
                data = currentWeatherResponse
            )
        } catch (exception: UnknownHostException) {
            mutableCurrentWeatherState.value = ResourceState.Error(
                resourceMessageId = R.string.internet_connection_error
            )
        }
    }
}
