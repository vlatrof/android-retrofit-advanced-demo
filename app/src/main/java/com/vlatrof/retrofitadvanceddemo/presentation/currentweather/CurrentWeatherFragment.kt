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
import com.vlatrof.retrofitadvanceddemo.app.utils.showSnackbar
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentCurrentWeatherBinding
import com.vlatrof.retrofitadvanceddemo.presentation.common.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private val args: CurrentWeatherFragmentArgs by navArgs()
    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var binding: FragmentCurrentWeatherBinding
    private var isForecastButtonVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentWeatherBinding.bind(view)
        modifyActionBarMenu()
        observeCurrentWeather()
    }

    private fun observeCurrentWeather() {
        currentWeatherViewModel.currentWeatherState.observe(viewLifecycleOwner) { newState ->
            when (newState) {
                is BaseViewModel.ResourceState.Initial -> {
                    changeLayoutVisibility() // all params false (by default)
                }
                is BaseViewModel.ResourceState.Loading -> {
                    changeLayoutVisibility(isProgressBarVisible = true)
                }
                is BaseViewModel.ResourceState.Error -> {
                    binding.tvLoadErrorMessage.text = getString(
                        R.string.data_fetch_error_ui_message
                    )
                    showSnackbar(message = requireActivity().getString(newState.resourceMessageId))
                    changeLayoutVisibility(isErrorMessageVisible = true)
                }
                is BaseViewModel.ResourceState.Success -> {
                    binding.tvLoadedContentText.text = newState.data.toString()
                    changeLayoutVisibility(isContentVisible = true, isForecastButtonVisible = true)
                }
            }
            requireActivity().invalidateOptionsMenu()
        }
    }

    private fun modifyActionBarMenu() {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_current_weather_action_bar_menu, menu)
                    val item = menu.findItem(R.id.menu_btn_show_weather_forecast)
                    item.isEnabled = isForecastButtonVisible
                    item.isVisible = isForecastButtonVisible
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

    private fun changeLayoutVisibility(
        isProgressBarVisible: Boolean = false,
        isContentVisible: Boolean = false,
        isErrorMessageVisible: Boolean = false,
        isForecastButtonVisible: Boolean = false
    ) {
        binding.pbLoadingContent.visibility =
            if (isProgressBarVisible) View.VISIBLE else View.INVISIBLE

        binding.tvLoadedContentText.visibility =
            if (isContentVisible) View.VISIBLE else View.INVISIBLE

        binding.tvLoadErrorMessage.visibility =
            if (isErrorMessageVisible) View.VISIBLE else View.INVISIBLE

        this@CurrentWeatherFragment.isForecastButtonVisible = isForecastButtonVisible
    }

    private fun navigateToWeatherForecast() {
        findNavController().navigate(
            CurrentWeatherFragmentDirections.actionFragmentCurrentWeatherToFragmentWeatherForecast(
                cityName = args.cityName
            )
        )
    }
}
