package fr.ippon.weatherflow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import fr.ippon.weatherflow.api.CityWeatherResultDeserializer
import fr.ippon.weatherflow.api.CityWeatherRouting
import fr.ippon.weatherflow.db.CityWeather
import fr.ippon.weatherflow.db.CityWeatherDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

class CityWeatherRepository constructor(
    private val cityWeatherDao: CityWeatherDao,
    private val cityWeatherResultDeserializer: CityWeatherResultDeserializer
) {
    private val _cityWeathers = MediatorLiveData<Resource<Array<CityWeather>>>()
    val cityWeathers: LiveData<Resource<Array<CityWeather>>>
        get() {
            return _cityWeathers
        }

    init {
        subscribeToDatabase()
    }

    private fun subscribeToDatabase() {
        val sourceDb = cityWeatherDao.getAll().asLiveData()
        _cityWeathers.postValue(Resource.loading(emptyArray()))

        _cityWeathers.addSource(sourceDb) {
            _cityWeathers.postValue(Resource.success(it))
        }
    }

    private fun CoroutineScope.getCityWeathers() = produce {
        val lastData: Array<CityWeather> = cityWeathers.value?.data ?: emptyArray()
        send(Resource.loading(lastData))

        Fuel.request(CityWeatherRouting.get(null, null))
            .awaitObjectResult(cityWeatherResultDeserializer)
            .fold(success = { response ->
                cityWeatherDao.insertAll(response.data)
            }, failure = { error ->
                send(Resource.error(error, lastData))
            })
    }

    fun fetchCityWeathers() = GlobalScope.launch {
        for (cityWeather in getCityWeathers()) {
            _cityWeathers.postValue(cityWeather)
        }
    }
}