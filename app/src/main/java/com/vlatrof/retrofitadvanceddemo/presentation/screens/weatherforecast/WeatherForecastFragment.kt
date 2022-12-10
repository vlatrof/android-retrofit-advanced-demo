package com.vlatrof.retrofitadvanceddemo.presentation.screens.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentWeatherForecastBinding

class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    private lateinit var binding: FragmentWeatherForecastBinding
    private val args: WeatherForecastFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherForecastBinding.bind(view)
    }
}
