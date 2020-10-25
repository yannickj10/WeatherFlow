package fr.ippon.weatherflow.di

import androidx.room.Room
import fr.ippon.weatherflow.db.CityWeatherDatabase
import org.koin.dsl.module

private const val DATABASE_NAME = "CITY_WEATHER_DB"

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), CityWeatherDatabase::class.java, DATABASE_NAME)
            .build()
    }
}