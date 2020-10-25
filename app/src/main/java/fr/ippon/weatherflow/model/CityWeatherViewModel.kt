package fr.ippon.weatherflow.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fr.ippon.weatherflow.db.CityWeather
import fr.ippon.weatherflow.repository.CityWeatherRepository
import fr.ippon.weatherflow.repository.Resource

class CityWeatherViewModel constructor(
    private val cityWeatherRepository: CityWeatherRepository
): ViewModel() {
    val cityWeathers: LiveData<Resource<Array<CityWeather>>> = cityWeatherRepository.cityWeathers

    fun fetchCityWeathers() {
        cityWeatherRepository.fetchCityWeathers()
    }
}