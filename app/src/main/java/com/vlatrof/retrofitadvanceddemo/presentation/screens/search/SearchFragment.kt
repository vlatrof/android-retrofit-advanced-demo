package com.vlatrof.retrofitadvanceddemo.presentation.screens.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.vlatrof.retrofitadvanceddemo.BuildConfig
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.data.remote.retrofit.WeatherRetrofit
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentSearchBinding
import com.vlatrof.retrofitadvanceddemo.presentation.shared.utils.hideKeyboard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        binding.btnSearch.isEnabled = false
        binding.etSearchInput.doAfterTextChanged { newValue ->
            newValue?.let { binding.btnSearch.isEnabled = newValue.isNotBlank() }
        }
        binding.btnSearch.setOnClickListener {
            hideKeyboard()

            GlobalScope.launch {
                val coordinatesResponse = WeatherRetrofit.api.getCoordinates(
                    cityName = binding.etSearchInput.text.toString().trim(),
                    limit = 1,
                    apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                )
                val coordinatesList = coordinatesResponse.body()
                val targetCoordinates = coordinatesList!![0]
                val forecastResponse = WeatherRetrofit.api.getWeatherForecast(
                    lat = targetCoordinates.lat,
                    lon = targetCoordinates.lon,
                    units = "metric",
                    apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                )
                Log.e("TAG", "RESULT: ${
                    forecastResponse.body()?.list?.get(0)?.toString()
                }")
            }

            // findNavController().navigate(
            //     SearchFragmentDirections.actionFragmentSearchToFragmentWeatherToday(
            //         cityName = binding.etSearchInput.text.toString()
            //     )
            // )
        }
    }
}
