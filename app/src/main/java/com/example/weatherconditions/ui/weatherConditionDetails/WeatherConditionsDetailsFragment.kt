package com.example.weatherconditions.ui.weatherConditionDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherconditions.R
import com.example.weatherconditions.databinding.FragmentWeatherConditionsDetailsBinding
import com.example.weatherconditions.model.weatherCondition.ConditionOperator
import com.example.weatherconditions.model.weatherCondition.ConditionType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherConditionsDetailsFragment : Fragment(R.layout.fragment_weather_conditions_details) {

    // used on init to try to find an existin condition
    private val args : WeatherConditionsDetailsFragmentArgs by navArgs()
    private val db_tag = "WeatherConditionsDetailsFragment"

    private lateinit var binding: FragmentWeatherConditionsDetailsBinding
    private val viewModel : WeatherConditionDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWeatherConditionsDetailsBinding.bind(view)

        setUpSpinner(binding.spinnerConditionType, R.array.condition_types_array)
        setUpSpinner(binding.spinnerConditionOperator, R.array.condition_operators_array)

        binding.inputConditionName.doAfterTextChanged {
            viewModel.nameTextChanged(it.toString())
        }

        binding.radioGroupIcon.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radio_no_icon -> viewModel.iconRadioChanged("ic_launcher_foreground")
                R.id.radio_sun -> viewModel.iconRadioChanged("ic_sun_icon")
                R.id.radio_wind -> viewModel.iconRadioChanged("ic_wind_icon")
            }
        }

        binding.spinnerConditionType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> viewModel.conditionTypeChanged(ConditionType.TEMPERATURE)
                    1 -> viewModel.conditionTypeChanged(ConditionType.WIND_STRENGTH)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.spinnerConditionOperator.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> viewModel.conditionOperatorChanged(ConditionOperator.BIGGER_THAN)
                    1 -> viewModel.conditionOperatorChanged(ConditionOperator.SMALLER_THAN)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.inputConditionValue.doAfterTextChanged {
            viewModel.valueTextChanged(it.toString())
        }

        binding.saveWeatherConditionButton.setOnClickListener {
            viewModel.addEditWeatherCondition() // values are already set in viewModel
            findNavController().popBackStack()
        }

        binding.deleteWeatherConditionButton.setOnClickListener {
            viewModel.deleteWeatherCondition()
            findNavController().popBackStack()
        }

        // if we find a weatherCondition by it's id, update it async
        viewModel.weatherCondition.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.inputConditionName.setText(it.name)
                when (it.conditionType) {
                    ConditionType.TEMPERATURE -> binding.spinnerConditionType.setSelection(0)
                    ConditionType.WIND_STRENGTH -> binding.spinnerConditionType.setSelection(1)
                }
                when (it.conditionOperator) {
                    ConditionOperator.BIGGER_THAN -> binding.spinnerConditionOperator.setSelection(0)
                    ConditionOperator.SMALLER_THAN -> binding.spinnerConditionOperator.setSelection(1)
                }
                binding.inputConditionValue.setText(it.conditionValue.toString())

                when (it.conditionIcon) {
                    "ic_sun_icon" -> binding.radioGroupIcon.check(R.id.radio_sun)
                    "ic_wind_icon" -> binding.radioGroupIcon.check(R.id.radio_wind)
                    else -> binding.radioGroupIcon.check(R.id.radio_no_icon)
                }

                // if a weatherCondition was loaded into view, user can delete it
                binding.deleteWeatherConditionButton.isEnabled = true

            }
        }
        viewModel.setWeatherCondition(args.weatherConditionId)
    }

    private fun setUpSpinner(spinner: Spinner, arrayIdentifier : Int ) {
        ArrayAdapter.createFromResource(
            requireContext(),
            arrayIdentifier,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
}