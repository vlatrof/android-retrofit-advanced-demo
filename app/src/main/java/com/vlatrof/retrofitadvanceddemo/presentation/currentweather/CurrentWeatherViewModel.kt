package com.vlatrof.retrofitadvanceddemo.presentation.currentweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.data.di.MainDispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.CurrentWeather
import com.vlatrof.retrofitadvanceddemo.domain.GetCurrentWeatherUseCase
import com.vlatrof.retrofitadvanceddemo.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(

    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    savedStateHandle: SavedStateHandle

) : BaseViewModel() {
    
    private val mutableCurrentWeatherLiveData =
        MutableLiveData<ResourceState<CurrentWeather>>(ResourceState.Initial)
    val currentWeatherLiveData: LiveData<ResourceState<CurrentWeather>> =
        mutableCurrentWeatherLiveData

    init {
        fetchCurrentWeather(
            cityName = CurrentWeatherFragmentArgs
                .fromSavedStateHandle(savedStateHandle = savedStateHandle)
                .cityName,
            units = "metric" // todo: value should be taken from shared prefs
        )
    }

    private fun fetchCurrentWeather(cityName: String, units: String) {
        mutableCurrentWeatherLiveData.value = ResourceState.Loading

        viewModelScope.launch(mainDispatcher) {
            val currentWeatherResult = getCurrentWeatherUseCase(
                cityName = cityName,
                units = units
            )

            when (currentWeatherResult) {
                is GetCurrentWeatherUseCase.Result.UnknownHostError -> {
                    mutableCurrentWeatherLiveData.value = ResourceState.Error(
                        resourceMessageId = R.string.internet_connection_error
                    )
                }
                is GetCurrentWeatherUseCase.Result.NotFoundError -> {
                    mutableCurrentWeatherLiveData.value = ResourceState.Error(
                        resourceMessageId = R.string.no_such_city_was_found_error
                    )
                }
                is GetCurrentWeatherUseCase.Result.Success -> {
                    mutableCurrentWeatherLiveData.value = ResourceState.Success(
                        data = currentWeatherResult.data
                    )
                }
            }
        }
    }
}
