package fr.ippon.weatherflow.di

import fr.ippon.weatherflow.api.CityWeatherResultDeserializer
import fr.ippon.weatherflow.api.CityWeatherService
import org.koin.dsl.module

val apiModule = module {
    single { CityWeatherResultDeserializer(get()) }
    single { CityWeatherService(get()) }
}
