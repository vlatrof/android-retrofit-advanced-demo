package com.vlatrof.retrofitadvanceddemo.presentation.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.app.utils.showSnackbar
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentWeatherForecastBinding
import com.vlatrof.retrofitadvanceddemo.presentation.common.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

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
                    binding.tvWeatherForecast.text = getString(
                        R.string.data_fetch_error_ui_message
                    )
                    showSnackbar(message = requireActivity().getString(newState.resourceMessageId))
                }
                is BaseViewModel.ResourceState.Success -> {
                    binding.pbWeatherForecastLoading.visibility = View.INVISIBLE
                    binding.tvWeatherForecast.text = newState.data.toString()
                }
            }
        }
    }
}
