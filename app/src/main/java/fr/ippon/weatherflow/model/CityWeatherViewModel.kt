package fr.ippon.weatherflow.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import fr.ippon.weatherflow.db.CityWeather
import fr.ippon.weatherflow.repository.CityWeatherRepository
import fr.ippon.weatherflow.repository.Resource
import fr.ippon.weatherflow.repository.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CityWeatherViewModel constructor(
    private val cityWeatherRepository: CityWeatherRepository
): ViewModel() {
    val cityWeathers: LiveData<Array<CityWeather>> = cityWeatherRepository.cityWeathers.asLiveData(Dispatchers.IO)

    fun fetchCityWeathers() = viewModelScope.async {
        cityWeatherRepository.refreshCityWeathers()
    }
}