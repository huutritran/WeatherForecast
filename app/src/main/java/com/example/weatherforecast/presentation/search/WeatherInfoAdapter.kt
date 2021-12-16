package com.example.weatherforecast.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.constant.Config
import com.example.weatherforecast.databinding.ItemWeatherInfoBinding
import com.example.weatherforecast.domain.models.WeatherInfo
import com.example.weatherforecast.extension.toDisplayString

data class WeatherInfoItem(
    val date: String,
    val averageTemp: String,
    val pressure: String,
    val humidity: String,
    val description: String
) {
    companion object {
        fun fromWeatherInfo(weatherInfo: WeatherInfo, context: Context): WeatherInfoItem {
            return WeatherInfoItem(
                date = weatherInfo.date.toDisplayString(Config.Date.defaultFormat),
                averageTemp = tempWithUnits(weatherInfo.averageTemperature, context),
                pressure = weatherInfo.pressure.toInt().toString(),
                humidity = weatherInfo.humidity.toString(),
                description = weatherInfo.description
            )
        }

        private fun tempWithUnits(temp: Double, context: Context): String {
            val tempInt = temp.toInt()
            return when (Config.DefaultQuery.units) {
                Config.TemperatureUnit.Metric -> context.getString(
                    R.string.temperature_with_celsius,
                    tempInt
                )
                Config.TemperatureUnit.Imperial -> context.getString(
                    R.string.temperature_with_fahrenheit,
                    tempInt
                )
                else -> tempInt.toString()
            }
        }
    }
}


class WeatherInfoAdapter : RecyclerView.Adapter<WeatherInfoAdapter.WeatherInfoViewHolder>() {

    private val items = mutableListOf<WeatherInfoItem>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding =
            DataBindingUtil.inflate<ItemWeatherInfoBinding>(inflater, R.layout.item_weather_info, parent, false)
        return WeatherInfoViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<WeatherInfoItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    class WeatherInfoViewHolder(private val viewDataBinding: ItemWeatherInfoBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(data: WeatherInfoItem) {
            viewDataBinding.weather = data
        }
    }
}
