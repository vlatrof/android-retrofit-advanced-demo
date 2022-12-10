package com.vlatrof.retrofitadvanceddemo.presentation.screens.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentWeatherForecastBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    private val args: WeatherForecastFragmentArgs by navArgs()
    private val weatherForecastViewModel: WeatherForecastViewModel by viewModels()
    private lateinit var binding: FragmentWeatherForecastBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherForecastBinding.bind(view)

        weatherForecastViewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { newValue ->
            binding.tvWeatherForecastText.text = newValue.toString()
        }
    }
}
