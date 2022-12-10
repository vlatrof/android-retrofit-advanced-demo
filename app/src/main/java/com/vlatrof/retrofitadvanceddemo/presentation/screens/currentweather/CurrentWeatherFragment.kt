package com.vlatrof.retrofitadvanceddemo.presentation.screens.currentweather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentCurrentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private val args: CurrentWeatherFragmentArgs by navArgs()
    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var binding: FragmentCurrentWeatherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentWeatherBinding.bind(view)
        modifyActionBarMenu()
        observeCurrentWeather()
    }

    private fun observeCurrentWeather() {
        currentWeatherViewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { newValue ->
            binding.tvCurrentWeatherText.text = newValue.toString()
        }
    }

    private fun modifyActionBarMenu() {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_current_weather_action_bar_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_btn_show_weather_forecast) {
                        navigateToWeatherForecast()
                        return true
                    }
                    return false
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        )
    }

    private fun navigateToWeatherForecast() {
        findNavController().navigate(
            CurrentWeatherFragmentDirections.actionFragmentCurrentWeatherToFragmentWeatherForecast(
                cityName = args.cityName
            )
        )
    }
}
