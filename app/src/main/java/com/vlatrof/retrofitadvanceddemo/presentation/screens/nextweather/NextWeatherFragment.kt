package com.vlatrof.retrofitadvanceddemo.presentation.screens.nextweather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentNextWeatherBinding
import com.vlatrof.retrofitadvanceddemo.presentation.screens.weathertoday.WeatherTodayFragmentArgs

class NextWeatherFragment : Fragment(R.layout.fragment_next_weather) {

    private lateinit var binding: FragmentNextWeatherBinding
    private val args: WeatherTodayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNextWeatherBinding.bind(view)
        binding.tvCitynameNext.text = "${args.cityName} + 3 days" // TODO: DUMMY!
    }
}
