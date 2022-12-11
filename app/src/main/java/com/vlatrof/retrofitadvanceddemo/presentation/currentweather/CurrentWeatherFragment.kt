package com.vlatrof.retrofitadvanceddemo.presentation.currentweather

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
import com.vlatrof.retrofitadvanceddemo.presentation.shared.BaseViewModel
import com.vlatrof.retrofitadvanceddemo.app.utils.showToast
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

    private fun observeCurrentWeather() {
        currentWeatherViewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { newState ->
            when (newState) {
                is BaseViewModel.ResourceState.Initial -> {
                    binding.pbCurrentWeatherLoading.visibility = View.INVISIBLE
                }

                is BaseViewModel.ResourceState.Loading -> {
                    binding.pbCurrentWeatherLoading.visibility = View.VISIBLE
                }

                is BaseViewModel.ResourceState.Error -> {
                    binding.pbCurrentWeatherLoading.visibility = View.INVISIBLE
                    showToast("Dummy Error")
                }

                is BaseViewModel.ResourceState.Success -> {
                    binding.pbCurrentWeatherLoading.visibility = View.INVISIBLE
                    binding.tvCurrentWeather.text = newState.data.toString()
                }
            }
        }
    }

    private fun navigateToWeatherForecast() {
        findNavController().navigate(
            CurrentWeatherFragmentDirections.actionFragmentCurrentWeatherToFragmentWeatherForecast(
                cityName = args.cityName
            )
        )
    }
}
