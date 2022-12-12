package com.vlatrof.retrofitadvanceddemo.domain

import com.vlatrof.retrofitadvanceddemo.data.di.IODispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.CurrentWeather
import com.vlatrof.retrofitadvanceddemo.data.remote.currentweather.CurrentWeatherRemoteDataSource
import com.vlatrof.retrofitadvanceddemo.data.remote.geocoordinates.GeoCoordinatesRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCurrentWeatherUseCase @Inject constructor(

    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val geoCoordinatesRemoteDataSource: GeoCoordinatesRemoteDataSource,
    private val currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource
    
) {
    
    sealed class Result {
        class Success(val data: CurrentWeather) : Result()
        object UnknownHostError : Result()
        object NotFoundError : Result()
    }
    
    suspend operator fun invoke(cityName: String, units: String): Result = withContext(
        ioDispatcher
    ) {
        val geoCoordinatesResponse = geoCoordinatesRemoteDataSource
            .getGeoCoordinates(cityName = cityName)

        when (geoCoordinatesResponse) {
            is GeoCoordinatesRemoteDataSource.Result.UnknownHostError -> {
                return@withContext Result.UnknownHostError
            }
            is GeoCoordinatesRemoteDataSource.Result.NotFoundError -> {
                return@withContext Result.NotFoundError
            }
            is GeoCoordinatesRemoteDataSource.Result.Success -> {
                val firstMatch = geoCoordinatesResponse.data[0]
                val currentWeatherResponse = currentWeatherRemoteDataSource.getCurrentWeather(
                    lat = firstMatch.lat,
                    lon = firstMatch.lon,
                    units = units
                )
                when (currentWeatherResponse) {
                    is CurrentWeatherRemoteDataSource.Result.UnknownHostError -> {
                        return@withContext Result.UnknownHostError
                    }
                    is CurrentWeatherRemoteDataSource.Result.WrongRequestDataError -> {
                        return@withContext Result.UnknownHostError
                    }
                    is CurrentWeatherRemoteDataSource.Result.Success -> {
                        return@withContext Result.Success(
                            data = currentWeatherResponse.data
                        )
                    }
                }
            }
        }
    }
}
