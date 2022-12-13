package com.vlatrof.retrofitadvanceddemo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp : Application()

// todo: Temperature units value should be taken from shared prefs
// todo: Add repositories
// todo: Add layer-specific models
// todo: Implement correct checking of weatherApi responses
// todo: Complete screen layouts: Current Weather Screen, Weather Forecast Screen 
