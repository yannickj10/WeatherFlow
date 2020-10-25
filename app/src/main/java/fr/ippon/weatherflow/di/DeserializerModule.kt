package fr.ippon.weatherflow.di

import fr.ippon.weatherflow.api.CityWeatherResultDeserializer
import org.koin.dsl.module

val deserializerModule = module {
    single { CityWeatherResultDeserializer(get()) }
}