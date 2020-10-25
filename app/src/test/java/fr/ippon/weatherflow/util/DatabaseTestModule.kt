package fr.ippon.weatherflow.util

import androidx.room.Room
import fr.ippon.weatherflow.db.CityWeatherDatabase
import org.koin.dsl.module

val databaseTestModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), CityWeatherDatabase::class.java).build()
    }
}
