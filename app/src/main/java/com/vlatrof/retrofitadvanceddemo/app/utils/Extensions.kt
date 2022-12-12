package com.vlatrof.retrofitadvanceddemo.app.utils // ktlint-disable filename

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

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

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireActivity(), message, length).show()
}

// Show Snackbar

fun Fragment.showSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
    view?.let { view -> Snackbar.make(view, message, length).show() }
}
