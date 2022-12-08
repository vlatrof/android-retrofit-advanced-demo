package com.vlatrof.retrofitadvanceddemo.presentation.screens.weathertoday

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentWeatherTodayBinding

class WeatherTodayFragment : Fragment(R.layout.fragment_weather_today) {

    private lateinit var binding: FragmentWeatherTodayBinding
    private val args: WeatherTodayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherTodayBinding.bind(view)
        binding.tvCityname.text = "${args.cityName} today" // TODO: DUMMY!
        modifyActionBarMenu()
    }

    private fun modifyActionBarMenu() {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_weather_today_action_bar_menu, menu)
                }
                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_btn_show_next_weather) {
                        navigateToNextWeather()
                        return true
                    }
                    return false
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        )
    }

    private fun navigateToNextWeather() {
        findNavController().navigate(
            WeatherTodayFragmentDirections.actionFragmentWeatherTodayToFragmentNextWeather(
                cityName = args.cityName
            )
        )
    }
}
