package com.example.weatherconditions.ui.weatherConditionList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherconditions.R
import com.example.weatherconditions.WeatherConditionResultListAdapter
import com.example.weatherconditions.databinding.FragmentWeatherConditionsBinding

class WeatherConditionsFragment : Fragment(R.layout.fragment_weather_conditions) {

    private lateinit var binding : FragmentWeatherConditionsBinding
    private val viewModel: WeatherConditionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiate the binding
        binding = FragmentWeatherConditionsBinding.bind(view)

        // Setup navigation to new WeatherConditions, which when opened without an id should
        // open empty details page
        binding.addWeatherCondition.setOnClickListener {
            findNavController().navigate(R.id.action_weatherConditionsFragment_to_weatherConditionsDetailsFragment)
        }

        // Setup the recycler view
        val recyclerView = binding.recyclerView
        val adapter = WeatherConditionResultListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        // Let us observe the weatherConditions-list and update the recycler view when it updates
        viewModel.getWeatherConditionResults().observe(viewLifecycleOwner) {
            // Update the cached copy of the words in the adapter.
            it?.let { adapter.submitList(it) }
        }

        viewModel.setWeatherConditionResults()

    }
}