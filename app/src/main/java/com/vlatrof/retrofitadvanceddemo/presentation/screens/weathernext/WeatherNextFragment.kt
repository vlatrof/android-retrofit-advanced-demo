package com.vlatrof.retrofitadvanceddemo.presentation.screens.weathernext

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentWeatherNextBinding
import com.vlatrof.retrofitadvanceddemo.presentation.screens.weathertoday.WeatherTodayFragmentArgs

class WeatherNextFragment : Fragment(R.layout.fragment_weather_next) {

    private lateinit var binding: FragmentWeatherNextBinding
    private val args: WeatherTodayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherNextBinding.bind(view)
    }
}
