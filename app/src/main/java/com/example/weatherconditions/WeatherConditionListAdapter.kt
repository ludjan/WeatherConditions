package com.example.weatherconditions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherconditions.databinding.ElementConditionBinding
import com.example.weatherconditions.model.WeatherConditionResult
import com.example.weatherconditions.model.weatherCondition.ConditionOperator
import com.example.weatherconditions.model.weatherCondition.ConditionType
import com.example.weatherconditions.ui.weatherConditionList.WeatherConditionsFragmentDirections

class WeatherConditionResultListAdapter :
    ListAdapter<WeatherConditionResult, WeatherConditionResultListAdapter.WeatherConditionResultViewHolder>
        (WeatherConditionResultComparator()) {

    private val dbTag = "WeatherConditionsResultListAdapter"

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WeatherConditionResultViewHolder {
        return WeatherConditionResultViewHolder.create(parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: WeatherConditionResultViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class WeatherConditionResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dbTag = "WeatherConditionsResultViewHolder"

        private val binding = ElementConditionBinding.bind(view)

        fun bind(weatherConditionResult: WeatherConditionResult) {

            // set the visible text
            binding.conditionName.text = weatherConditionResult.weatherCondition.name

            val resourceId = itemView.context.resources.getIdentifier(
                weatherConditionResult.weatherCondition.conditionIcon,
                "drawable",
                itemView.context.packageName
            )
            binding.conditionImage.setImageResource(resourceId)

            // set the result image
            if (weatherConditionResult.result) {
                binding.conditionMet.setImageResource(R.drawable.ic_checkmark)
            } else {
                binding.conditionMet.setImageResource(R.drawable.ic_cross)
            }

            // set to navigate on click
            binding.favoriteCardview.setOnClickListener {
                val action = WeatherConditionsFragmentDirections.
                    actionWeatherConditionsFragmentToWeatherConditionsDetailsFragment(
                        weatherConditionResult.weatherCondition.id!!
                    )
                itemView.findNavController().navigate(action)
            }

            val genConType = when (weatherConditionResult.weatherCondition.conditionType) {
                ConditionType.TEMPERATURE -> "Temperature"
                ConditionType.WIND_STRENGTH -> "Wind strength"
            }
            val genConOp = when (weatherConditionResult.weatherCondition.conditionOperator) {
                ConditionOperator.BIGGER_THAN -> "more than"
                ConditionOperator.SMALLER_THAN -> "less than"
            }

            val genConRes = when (weatherConditionResult.result) {
                true -> ""
                false -> "not "
            }

            binding.conditionGeneratedDescription.text =
                "$genConType is $genConRes$genConOp ${weatherConditionResult.weatherCondition.conditionValue}"
        }

        companion object {
            fun create(parent: ViewGroup): WeatherConditionResultViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.element_condition, parent, false)
                return WeatherConditionResultViewHolder(view)
            }
        }
    }

    class WeatherConditionResultComparator : DiffUtil.ItemCallback<WeatherConditionResult>() {
        override fun areItemsTheSame(
            oldItem: WeatherConditionResult,
            newItem: WeatherConditionResult
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherConditionResult,
            newItem: WeatherConditionResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}
