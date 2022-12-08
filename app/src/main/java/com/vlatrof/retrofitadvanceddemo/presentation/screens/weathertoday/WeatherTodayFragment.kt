package com.vlatrof.retrofitadvanceddemo.presentation.screens.weathertoday

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentWeatherTodayBinding

class WeatherTodayFragment : Fragment(R.layout.fragment_weather_today) {

    private lateinit var binding: FragmentWeatherTodayBinding
    private val args: WeatherTodayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherTodayBinding.bind(view)

        binding.tvCityName.text = args.cityName
    }
}
