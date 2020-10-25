package fr.ippon.weatherflow.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityWeather::class], version = 1, exportSchema = false)
abstract class CityWeatherDatabase : RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao
}