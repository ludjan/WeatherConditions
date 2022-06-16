package com.example.weatherconditions.ui.weatherConditionDetails

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherconditions.WeatherConditionRepository
import com.example.weatherconditions.model.weatherCondition.ConditionOperator
import com.example.weatherconditions.model.weatherCondition.ConditionType
import com.example.weatherconditions.model.weatherCondition.WeatherCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherConditionDetailsViewModel @Inject constructor(
    private val repository: WeatherConditionRepository,
): ViewModel() {

    private val _dbTag = "WeatherConditionDetailsViewModel"

    private var _weatherCondition = MutableLiveData<WeatherCondition>()
    private lateinit var conditionName: String
    private lateinit var conditionType: ConditionType
    private lateinit var conditionOperator: ConditionOperator
    private lateinit var conditionValue: String
    private var conditionId = -1

    fun setWeatherCondition(id: Int) {
        viewModelScope.launch {
            repository.getWeatherConditionById(id).let {
                if (it != null) {
                    _weatherCondition.postValue(it)
                    conditionName = it.name
                    conditionType = it.conditionType
                    conditionOperator = it.conditionOperator
                    conditionValue = it.conditionValue.toString()
                    conditionId = it.id!!
                }
            }
            Log.d(_dbTag, "WeatherCondition was updated in viewModel")
        }
    }

    val weatherCondition: LiveData<WeatherCondition> get() = _weatherCondition

    fun nameTextChanged(s: String) {
        conditionName = s
        Log.d(_dbTag, "nameTextChanged, new string is {$s}")
    }

    fun valueTextChanged(s: String) {
        conditionValue = s
        Log.d(_dbTag, "valueTextChanged, new value is {$s}")
    }

    fun conditionTypeChanged(type: ConditionType) {
        conditionType = type
        Log.d(_dbTag, "conditionTypeChanged, new type is {$type}")
    }

    fun conditionOperatorChanged(operator: ConditionOperator) {
        conditionOperator = operator
        Log.d(_dbTag, "conditionOperatorChanged, new type is {$operator}")
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun addEditWeatherCondition() = viewModelScope.launch {
        if (conditionName.isNotBlank() && conditionValue.isNotBlank()) {
            val newWeatherCondition = WeatherCondition(
                conditionId,
                conditionName,
                conditionType,
                conditionOperator,
                conditionValue.toDouble()
            )
            repository.insertWeatherCondition(newWeatherCondition)
        } else {
            Log.d(_dbTag, "Name cannot be empty")
        }
    }

    /**
     * Launching a new coroutine to delete the data in a non-blocking way
     */
    fun deleteWeatherCondition() = viewModelScope.launch {
        repository.deleteWeatherCondition(_weatherCondition.value!!)
    }
}