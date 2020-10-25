package fr.ippon.weatherflow.di

import fr.ippon.weatherflow.repository.CityWeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CityWeatherRepository(get(), get()) }
}