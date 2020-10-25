package fr.ippon.weatherflow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.ippon.weatherflow.R
import fr.ippon.weatherflow.db.CityWeather
import fr.ippon.weatherflow.repository.Resource
import fr.ippon.weatherflow.repository.Status
import fr.ippon.weatherflow.model.CityWeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private val cityWeatherViewModel: CityWeatherViewModel by viewModel()
    private val cityWeatherAdapter: CityWeatherAdapter by lazy {
        CityWeatherAdapter(cityWeathers, this)
    }

    private val cityWeathers: MutableList<CityWeather> = mutableListOf()

    private val updateCityWeather = Observer<Resource<Array<CityWeather>>> {
        refreshCityWeathersList(it.data ?: emptyArray())

        when (it.status) {
            Status.SUCCESS -> {
                this@MainActivity.swipe_refresh.isRefreshing = false
            }
            Status.ERROR -> {
                Toast.makeText(this@MainActivity, it.throwable?.message, Toast.LENGTH_SHORT).show()
                this@MainActivity.swipe_refresh.isRefreshing = false
            }
            Status.LOADING -> this@MainActivity.swipe_refresh.isRefreshing = true
        }
    }

    private fun refreshCityWeathersList(newCityWeathers: Array<CityWeather>) {
        cityWeathers.clear()
        cityWeathers.addAll(newCityWeathers)
        cityWeatherAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        init()
    }

    private fun init() {
        val layoutManager = LinearLayoutManager(this)
        this.recycler_view_city.layoutManager = layoutManager
        this.recycler_view_city.adapter = cityWeatherAdapter
        this.swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this,
            R.color.colorAccent
        ))
        this.cityWeatherViewModel.cityWeathers.observe(this, this.updateCityWeather)
    }

    override fun onResume() {
        super.onResume()

        this.cityWeatherViewModel.fetchCityWeathers()
        this.swipe_refresh.setOnRefreshListener {
            this.cityWeatherViewModel.fetchCityWeathers()
        }
    }

    override fun onPause() {
        this.swipe_refresh.setOnRefreshListener(null)
        super.onPause()
    }
}