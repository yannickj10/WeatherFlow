package fr.ippon.weatherflow.di

import fr.ippon.weatherflow.db.CityWeatherDatabase
import org.koin.dsl.module

val daoModule = module {
    single {
        get<CityWeatherDatabase>().cityWeatherDao()
    }
}