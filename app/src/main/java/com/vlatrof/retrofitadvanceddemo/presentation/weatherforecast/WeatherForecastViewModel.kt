package com.vlatrof.retrofitadvanceddemo.presentation.weatherforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.data.di.MainDispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherForecast
import com.vlatrof.retrofitadvanceddemo.domain.GetCurrentWeatherUseCase
import com.vlatrof.retrofitadvanceddemo.domain.GetWeatherForecastUseCase
import com.vlatrof.retrofitadvanceddemo.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(

    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    savedStateHandle: SavedStateHandle

) : BaseViewModel() {

    private val mutableWeatherForecastLiveData =
        MutableLiveData<ResourceState<WeatherForecast>>(ResourceState.Initial)
    val weatherForecastLiveData: LiveData<ResourceState<WeatherForecast>> =
        mutableWeatherForecastLiveData

    init {
        fetchWeatherForecast(
            cityName = WeatherForecastFragmentArgs
                .fromSavedStateHandle(savedStateHandle = savedStateHandle)
                .cityName,
            units = "metric" // todo: value should be taken from shared prefs
        )
    }

    private fun fetchWeatherForecast(cityName: String, units: String) {
        mutableWeatherForecastLiveData.value = ResourceState.Loading

        viewModelScope.launch(mainDispatcher) {
            val weatherForecastResult = getWeatherForecastUseCase(
                cityName = cityName,
                units = units
            )

            when (weatherForecastResult) {
                is GetWeatherForecastUseCase.Result.UnknownHostError -> {
                    mutableWeatherForecastLiveData.value = ResourceState.Error(
                        resourceMessageId = R.string.internet_connection_error
                    )
                }
                is GetWeatherForecastUseCase.Result.NotFoundError -> {
                    mutableWeatherForecastLiveData.value = ResourceState.Error(
                        resourceMessageId = R.string.no_such_city_was_found_error
                    )
                }
                is GetWeatherForecastUseCase.Result.Success -> {
                    mutableWeatherForecastLiveData.value = ResourceState.Success(
                        data = weatherForecastResult.data
                    )
                }
            }
        }
    }
}
