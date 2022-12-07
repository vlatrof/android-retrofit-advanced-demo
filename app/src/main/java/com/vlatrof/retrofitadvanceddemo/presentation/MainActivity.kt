package com.vlatrof.retrofitadvanceddemo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vlatrof.retrofitadvanceddemo.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarNavigation()
    }

    private fun setupActionBarNavigation() {
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
