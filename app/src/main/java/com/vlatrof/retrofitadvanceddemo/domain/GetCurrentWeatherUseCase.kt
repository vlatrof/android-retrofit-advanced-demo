package com.vlatrof.retrofitadvanceddemo.domain

import com.vlatrof.retrofitadvanceddemo.data.di.IODispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.CurrentWeather
import com.vlatrof.retrofitadvanceddemo.data.remote.currentweather.CurrentWeatherRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCurrentWeatherUseCase @Inject constructor(

    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource

) {

    suspend operator fun invoke(lat: Double, lon: Double, units: String): CurrentWeather? =
        withContext(ioDispatcher) {
            currentWeatherRemoteDataSource.getCurrentWeather(lat = lat, lon = lon, units = units)
        }
}
