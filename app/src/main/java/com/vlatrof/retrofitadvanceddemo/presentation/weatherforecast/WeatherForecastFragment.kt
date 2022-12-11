package com.vlatrof.retrofitadvanceddemo.presentation.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.app.utils.showToast
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentWeatherForecastBinding
import com.vlatrof.retrofitadvanceddemo.presentation.shared.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    private val args: WeatherForecastFragmentArgs by navArgs()
    private val weatherForecastViewModel: WeatherForecastViewModel by viewModels()
    private lateinit var binding: FragmentWeatherForecastBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherForecastBinding.bind(view)
        observeWeatherForecast()
    }

    private fun observeWeatherForecast() {
        weatherForecastViewModel.weatherForecastLiveData.observe(viewLifecycleOwner) { newState ->
            when (newState) {
                is BaseViewModel.ResourceState.Initial -> {
                    binding.pbWeatherForecastLoading.visibility = View.INVISIBLE
                }

                is BaseViewModel.ResourceState.Loading -> {
                    binding.pbWeatherForecastLoading.visibility = View.VISIBLE
                }

                is BaseViewModel.ResourceState.Error -> {
                    binding.pbWeatherForecastLoading.visibility = View.INVISIBLE
                    showToast("Dummy Error")
                }

                is BaseViewModel.ResourceState.Success -> {
                    binding.pbWeatherForecastLoading.visibility = View.INVISIBLE
                    binding.tvWeatherForecast.text = newState.data.toString()
                }
            }
        }
    }
}
