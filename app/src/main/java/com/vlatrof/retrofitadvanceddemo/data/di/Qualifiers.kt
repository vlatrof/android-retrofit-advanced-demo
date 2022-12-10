package com.vlatrof.retrofitadvanceddemo.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher
