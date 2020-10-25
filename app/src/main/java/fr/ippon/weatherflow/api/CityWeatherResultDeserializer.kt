package fr.ippon.weatherflow.api

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class CityWeatherResultDeserializer (private var gson: Gson) : ResponseDeserializable<CityWeatherResult> {

    override fun deserialize(content: String): CityWeatherResult {
       return gson.fromJson(content, CityWeatherResult::class.java)
    }
}