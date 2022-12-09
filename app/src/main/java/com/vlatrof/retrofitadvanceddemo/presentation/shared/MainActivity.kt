package com.vlatrof.retrofitadvanceddemo.presentation.shared

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vlatrof.retrofitadvanceddemo.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
    }

    private fun setupActionBar() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.root_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this@MainActivity, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (navController.navigateUp()) {
            return true
        }
        return super.onSupportNavigateUp()
    }
}
