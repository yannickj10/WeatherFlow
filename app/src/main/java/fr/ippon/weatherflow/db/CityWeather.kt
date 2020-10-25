package fr.ippon.weatherflow.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CITY_WEATHER")
data class CityWeather (
    @PrimaryKey
    val id: Long,
    val cityName: String,
    val weatherPrecision: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double
)