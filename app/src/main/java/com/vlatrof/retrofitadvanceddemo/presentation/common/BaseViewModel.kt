package com.vlatrof.retrofitadvanceddemo.presentation.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    sealed class ResourceState<out T> {
        object Initial : ResourceState<Nothing>()
        object Loading : ResourceState<Nothing>()
        data class Success<out T>(val data: T) : ResourceState<T>()
        data class Error(val resourceMessageId: Int) : ResourceState<Nothing>()
    }
}
