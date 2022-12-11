package com.vlatrof.retrofitadvanceddemo.app.utils // ktlint-disable filename

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

// Hide Keyboard

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

// Show Toast

fun Fragment.showToast(message: String, toastLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireActivity(), message, toastLength).show()
}
