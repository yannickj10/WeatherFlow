package fr.ippon.weatherflow.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import fr.ippon.weatherflow.R
import fr.ippon.weatherflow.db.CityWeather
import fr.ippon.weatherflow.viewmodel.CityWeatherViewModel
import fr.ippon.weatherflow.repository.Status
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val cityWeatherViewModel: CityWeatherViewModel by viewModel()
    private val cityWeatherAdapter: CityWeatherAdapter by lazy {
        CityWeatherAdapter(cityWeathers, this)
    }

    private val cityWeathers: MutableList<CityWeather> = mutableListOf()

    private val updateCityWeather = Observer<Array<CityWeather>> {
        refreshCityWeathersList(it ?: emptyArray())
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

        refreshCityWeathers()

        this.swipe_refresh.setOnRefreshListener {
            refreshCityWeathers()
        }
    }

    private fun refreshCityWeathers() = lifecycleScope.launch(Dispatchers.Default) {
        cityWeatherViewModel.fetchCityWeathers().await().collect { displayRequestStatus(it) }
    }

    private suspend fun displayRequestStatus(status: Status) = withContext(Dispatchers.Main) {
        when (status) {
            Status.SUCCESS -> {
                this@MainActivity.swipe_refresh.isRefreshing = false
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
            }
            Status.ERROR -> {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                this@MainActivity.swipe_refresh.isRefreshing = false
            }
            Status.LOADING -> {
                Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                this@MainActivity.swipe_refresh.isRefreshing = true
            }
        }
    }

    override fun onPause() {
        this.swipe_refresh.setOnRefreshListener(null)
        super.onPause()
    }
}