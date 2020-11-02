package fr.ippon.weatherflow

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import fr.ippon.weatherflow.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CityWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            initializeDebugConfiguration()
        }

        setUpDependencyInjection()
    }

    private fun setUpDependencyInjection() {
        startKoin {
            androidContext(this@CityWeatherApp)
            modules(
                listOf(
                    daoModule,
                    databaseModule,
                    apiModule,
                    jsonModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    private fun initializeDebugConfiguration() {
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
            .permitDiskReads()
                .detectDiskWrites()
                .detectAll()
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
    }
}
