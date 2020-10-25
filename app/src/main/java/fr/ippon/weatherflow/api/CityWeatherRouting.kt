package fr.ippon.weatherflow.api

import com.github.kittinunf.fuel.core.HeaderValues
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.util.FuelRouting
import fr.ippon.weatherflow.BuildConfig

sealed class CityWeatherRouting : FuelRouting {

    val apiKey = BuildConfig.API_KEY
    override val basePath = BuildConfig.API_BASE_URL

    class get(override val body: String? = "",
              override val bytes: ByteArray? = ByteArray(0)): CityWeatherRouting()

    override val method: Method
        get() {
            when(this) {
                is get -> return Method.GET
            }
        }

    override val path: String
        get() {
            return when(this) {
                is get -> "/group"
            }
        }

    override val params: List<Pair<String, Any?>>?
        get() {
            // Nantes : 2990969
            // Paris : 2968815
            return when(this) {
                is get -> listOf("id" to "2990969,2968815", "appid" to this.apiKey)
            }
        }

    override val headers: Map<String, HeaderValues>?
        get() {
            return null
        }
}