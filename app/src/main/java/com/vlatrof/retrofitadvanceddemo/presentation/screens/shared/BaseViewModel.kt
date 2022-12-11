package com.vlatrof.retrofitadvanceddemo.presentation.screens.shared

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    sealed class ResourceState<out T : Any> {
        object Initial : ResourceState<Nothing>()
        object Loading : ResourceState<Nothing>()
        data class Success<out T : Any>(val data: T) : ResourceState<T>()
        data class Error(val resourceMessageId: Int) : ResourceState<Nothing>()
    }
}
