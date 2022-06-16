package com.example.weatherconditions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherconditions.databinding.ActivityMainBinding
import com.example.weatherconditions.ui.weatherConditionList.WeatherConditionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherConditionViewModel by viewModels()
//    private val viewModel: WeatherConditionViewModel by viewModels {
//        WeatherConditionViewModelFactory((application as WeatherConditionsApplication).repository)
//    }
    private lateinit var navController: NavController
    private lateinit var binding : ActivityMainBinding
    private val logTag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(logTag, "MainActivity Started")

        // Set up navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}