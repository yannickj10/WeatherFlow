package fr.ippon.weatherflow.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fr.ippon.weatherflow.db.CityWeather
import java.util.*

data class CityWeatherResult (
    @Expose
    @SerializedName("list")
    val data: Array<CityWeather>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CityWeatherResult

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}