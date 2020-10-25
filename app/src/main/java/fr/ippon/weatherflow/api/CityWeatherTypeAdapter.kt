package fr.ippon.weatherflow.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import fr.ippon.weatherflow.db.CityWeather
import java.lang.reflect.Type
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class CityWeatherTypeAdapter : JsonDeserializer<CityWeather> {
    companion object {
        const val ID = "id"
        const val NAME = "name"

        const val WEATHER = "weather"
        const val DESCRIPTION = "description"

        const val MAIN = "main"
        const val TEMP = "temp"
        const val HUMIDITY = "humidity"

        const val WIND = "wind"
        const val SPEED = "speed"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CityWeather {
        val jsonCityWeather: JsonObject  = json?.asJsonObject ?: JsonObject()

        val id: Long = jsonCityWeather
            .get(ID)
            .asLong
        val cityName: String = jsonCityWeather
            .get(NAME)
            .asString

        val weatherArray = jsonCityWeather
            .get(WEATHER)
            .asJsonArray
        val weatherPrecision = weatherArray[0]
            .asJsonObject
            .get(DESCRIPTION)
            .asString

        val main = jsonCityWeather
            .get(MAIN)
            .asJsonObject
        val temperature: Double = main
            .get(TEMP)
            .asDouble
            .minus(273.15)

        val humidity: Double = main
            .get(HUMIDITY)
            .asDouble
        val windSpeed: Double = jsonCityWeather
            .get(WIND)
            .asJsonObject
            .get(SPEED)
            .asDouble.times(3.6)

        return CityWeather(id, cityName, weatherPrecision, String.format("%.2f", temperature).toDouble(), humidity, windSpeed)
    }
}