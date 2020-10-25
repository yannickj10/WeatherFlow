package fr.ippon.weatherflow.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cityWeathers: Array<CityWeather>)

    @Query ("SELECT * FROM CITY_WEATHER ORDER BY cityName ASC")
    fun getAll(): LiveData<Array<CityWeather>>
}