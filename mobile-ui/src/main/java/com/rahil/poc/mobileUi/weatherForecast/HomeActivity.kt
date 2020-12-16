package com.rahil.poc.mobileUi.weatherForecast

import android.content.Intent
import android.graphics.Color.parseColor
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rahil.poc.R
import com.rahil.poc.mobileUi.favouritecity.adapter.CityListAdapter
import com.rahil.poc.mobileUi.ext.get
import com.rahil.poc.mobileUi.favouritecity.CityListActivity
import com.rahil.poc.mobileUi.injection.ViewModelFactory
import com.rahil.poc.mobileUi.utils.Constants
import com.rahil.poc.mobileUi.utils.DateUtil
import com.rahil.poc.mobileUi.widget.error.ErrorListener
import com.rahil.poc.mobileUi.widget.error.ErrorView
import com.rahil.poc.presentation.model.WeatherViewDataModel
import com.rahil.poc.presentation.state.Resource
import com.rahil.poc.presentation.state.ResourceState
import com.rahil.poc.presentation.weatherForecast.WeatherForeCastViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: WeatherForeCastViewModel
    private lateinit var progress: ProgressBar
    private lateinit var errorView: ErrorView
    private lateinit var queryString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bar: ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(ColorDrawable(parseColor("#0d1f51")))
        viewModel = ViewModelProviders.of(this, viewModelFactory).get()
        progress = findViewById(R.id.progress_bar)
        errorView = findViewById(R.id.view_error)
        setupViewListeners()
        viewModel.getWeatherDetails().observe(this, dataStateObserver)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val item: MenuItem = menu!!.findItem(R.id.menu_search)
        val searchView: SearchView = item.getActionView() as SearchView
        searchView.setQueryHint(getString(R.string.enter_city_name))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryString = query!!
                viewModel.fetchWeatherData(queryString, Constants.METRICS_PARAM)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        super.onCreateOptionsMenu(menu)
        return true
    }

    private val dataStateObserver = Observer<Resource<WeatherViewDataModel>> { resource ->
        when (resource.status) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(resource.data)
            ResourceState.ERROR -> setupScreenForError(resource.message)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: WeatherViewDataModel?) {
        errorView.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null) {
            updateData(data)
        } else {
            errorView.visibility = View.VISIBLE
        }
    }

    private fun updateData(weatherData: WeatherViewDataModel) {
        Glide
            .with(this)
            .load(Constants.OPEN_WEATHER_IMAGE_API +  weatherData.icon + Constants.PNG_EXTENSION)
            .apply(RequestOptions().override(100, 100))
            .into(temp_image);
        currentTime.text = DateUtil.formatDateToDisplay(Calendar.getInstance().getTime());
        root_container.visibility = VISIBLE
        city.text = weatherData.city
        temp.text = getString(R.string.temp_string,  (weatherData.temp).toString())
        mintemp.text = getString(R.string.min_temp,  (weatherData.temp_min).toString())
        maxtemp.text = getString(R.string.max_temp,  (weatherData.temp_max).toString())
        humidity.text = getString(R.string.humidity, (weatherData.humidity).toString())
        pressure.text = getString(R.string.pressure, (weatherData.pressure).toString())
        sunrise.text = getString(R.string.sunrise, (DateUtil.unixTimeToFormatTime(this, weatherData.sunrise.toLong())))
        sunset.text = getString(R.string.sunset, (DateUtil.unixTimeToFormatTime(this, weatherData.sunset.toLong())))
        last_updated_time.text = getString(R.string.last_updated_time, (DateUtil.getLastUpdated(weatherData.lastUpdated.toLong())).toString())
    }

    private fun setupScreenForError(message: String?) {
        root_container.visibility = View.GONE
        progress.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        errorView.setCustomText(message!!)
    }


    private fun setupViewListeners() {
        save_to_fab.setOnClickListener({
            viewModel.saveAsFavouriteCity()
        })
        view_error.errorListener = errorListener
        go_to_fab_city.setOnClickListener {
            startActivity(Intent(this, CityListActivity::class.java))
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            viewModel.fetchWeatherData(queryString, Constants.METRICS_PARAM)
        }
    }
}



