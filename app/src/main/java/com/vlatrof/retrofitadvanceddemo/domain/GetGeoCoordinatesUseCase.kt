package com.vlatrof.retrofitadvanceddemo.domain

import com.vlatrof.retrofitadvanceddemo.data.di.IODispatcher
import com.vlatrof.retrofitadvanceddemo.data.remote.common.retrofit.GeoCoordinates
import com.vlatrof.retrofitadvanceddemo.data.remote.geocoordinates.GeoCoordinatesRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetGeoCoordinatesUseCase @Inject constructor(

    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val geoCoordinatesRemoteDataSource: GeoCoordinatesRemoteDataSource

) {

    suspend operator fun invoke(cityName: String): List<GeoCoordinates>? =
        withContext(ioDispatcher) {
            geoCoordinatesRemoteDataSource.getGeoCoordinates(cityName = cityName)
        }
}
