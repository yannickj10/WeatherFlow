package fr.ippon.weatherflow.repository

import fr.ippon.weatherflow.api.CityWeatherService
import fr.ippon.weatherflow.db.CityWeatherDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CityWeatherRepository constructor(
    private val cityWeatherDao: CityWeatherDao,
    private val cityWeatherService: CityWeatherService
) {
    val cityWeathers = cityWeatherDao.getAll().flowOn(Dispatchers.IO)

    suspend fun refreshCityWeathers() = flow {
        emit(Status.LOADING)

        cityWeatherService
            .getWeathers()
            .fold(success = { response ->
                emit(Status.SUCCESS)
                cityWeatherDao.insertAll(response.data)
            }, failure = {
                emit(Status.ERROR)
            })
    }.flowOn(Dispatchers.IO)
}