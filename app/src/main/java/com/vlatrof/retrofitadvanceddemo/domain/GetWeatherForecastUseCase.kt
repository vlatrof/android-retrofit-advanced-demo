package com.vlatrof.retrofitadvanceddemo.domain

import com.vlatrof.retrofitadvanceddemo.data.di.IODispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.WeatherForecast
import com.vlatrof.retrofitadvanceddemo.data.remote.geocoordinates.GeoCoordinatesRemoteDataSource
import com.vlatrof.retrofitadvanceddemo.data.remote.weatherforecast.WeatherForecastRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetWeatherForecastUseCase @Inject constructor(

    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val geoCoordinatesRemoteDataSource: GeoCoordinatesRemoteDataSource,
    private val weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource

) {

    sealed class Result {
        class Success(val data: WeatherForecast) : Result()
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
                val weatherForecastResponse = weatherForecastRemoteDataSource.getWeatherForecast(
                    lat = firstMatch.lat,
                    lon = firstMatch.lon,
                    units = units
                )
                when (weatherForecastResponse) {
                    is WeatherForecastRemoteDataSource.Result.UnknownHostError -> {
                        return@withContext Result.UnknownHostError
                    }
                    is WeatherForecastRemoteDataSource.Result.WrongRequestDataError -> {
                        return@withContext Result.UnknownHostError
                    }
                    is WeatherForecastRemoteDataSource.Result.Success -> {
                        return@withContext Result.Success(
                            data = weatherForecastResponse.data
                        )
                    }
                }
            }
        }
    }
}
