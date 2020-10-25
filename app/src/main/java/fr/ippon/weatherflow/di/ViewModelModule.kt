package fr.ippon.weatherflow.di

import fr.ippon.weatherflow.model.CityWeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CityWeatherViewModel(get()) }
}