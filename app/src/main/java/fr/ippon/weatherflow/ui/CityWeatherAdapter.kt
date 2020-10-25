package fr.ippon.weatherflow.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.ippon.weatherflow.R
import fr.ippon.weatherflow.db.CityWeather
import kotlinx.android.synthetic.main.city_weather_view.view.*


class CityWeatherAdapter(private val cityWeathers: List<CityWeather>, private val context: Context)
    : RecyclerView.Adapter<CityWeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.city_weather_view, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityWeather = this.cityWeathers[position]
        val cityName = cityWeather.cityName
        val temperature = cityWeather.temperature
        val precision = cityWeather.weatherPrecision
        val humidity = cityWeather.humidity
        val windSpeed = cityWeather.windSpeed

        holder.cityHeader.text = context.getString(
            R.string.city_header,
            cityName
        )

        holder.temperature.text = context.getString(
            R.string.temperature,
            temperature.toString()
        )

        holder.precision.text = precision

        holder.humidity.text = context.getString(
            R.string.humidity,
            humidity.toString()
        )

        holder.windSpeed.text = context.getString(
            R.string.windSpeed,
            windSpeed.toString()
        )
    }

    override fun getItemCount(): Int {
        return cityWeathers.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cityHeader: TextView = v.city_header
        val temperature: TextView = v.temperature
        val precision: TextView = v.precision
        val humidity: TextView = v.humidity
        val windSpeed: TextView = v.windSpeed
    }
}