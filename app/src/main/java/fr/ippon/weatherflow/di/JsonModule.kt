package fr.ippon.weatherflow.di

import com.google.gson.GsonBuilder
import fr.ippon.weatherflow.api.CityWeatherTypeAdapter
import fr.ippon.weatherflow.db.CityWeather
import org.koin.dsl.module
import java.lang.reflect.Modifier

val jsonModule = module {
    single {
        GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            .serializeNulls()
            .registerTypeAdapter(CityWeather::class.java, CityWeatherTypeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
}