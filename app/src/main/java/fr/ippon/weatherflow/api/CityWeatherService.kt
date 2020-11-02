package fr.ippon.weatherflow.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.result.Result

class CityWeatherService(private val cityWeatherResultDeserializer: CityWeatherResultDeserializer) {

    suspend fun getWeathers(): Result<CityWeatherResult, FuelError> {
        return Fuel.request(CityWeatherRouting.get(null, null))
            .awaitObjectResult(cityWeatherResultDeserializer)
    }
}