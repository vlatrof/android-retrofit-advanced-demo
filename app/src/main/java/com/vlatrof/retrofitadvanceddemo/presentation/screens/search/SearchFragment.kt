package com.vlatrof.retrofitadvanceddemo.presentation.screens.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vlatrof.retrofitadvanceddemo.R
import com.vlatrof.retrofitadvanceddemo.databinding.FragmentSearchBinding
import com.vlatrof.retrofitadvanceddemo.presentation.utils.hideKeyboard

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
            findNavController().navigate(
                 SearchFragmentDirections.actionFragmentSearchToFragmentCurrentWeather(
                     cityName = binding.etSearchInput.text.toString()
                 )
             )
        }
    }
}
