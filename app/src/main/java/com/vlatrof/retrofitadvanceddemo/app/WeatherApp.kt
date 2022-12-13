package com.vlatrof.retrofitadvanceddemo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp : Application()

// todo: Temperature units value should be taken from shared prefs
// todo: Add repositories and specific layer models
// todo: Implement correct checking of weatherApi responses
