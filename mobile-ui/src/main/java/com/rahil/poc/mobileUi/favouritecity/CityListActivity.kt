package com.rahil.poc.mobileUi.favouritecity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahil.poc.R
import com.rahil.poc.mobileUi.ext.get
import com.rahil.poc.mobileUi.favouritecity.adapter.CityListAdapter
import com.rahil.poc.mobileUi.injection.ViewModelFactory
import com.rahil.poc.mobileUi.widget.error.ErrorListener
import com.rahil.poc.mobileUi.widget.error.ErrorView
import com.rahil.poc.presentation.favouriteCity.FavouriteCityViewModel
import com.rahil.poc.presentation.model.WeatherViewDataModel
import com.rahil.poc.presentation.state.Resource
import com.rahil.poc.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class CityListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var cityListAdapter: CityListAdapter
    private lateinit var viewModel: FavouriteCityViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var errorView: ErrorView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get()
        recyclerView = findViewById(R.id.cityListRecyclerView)
        progress = findViewById(R.id.progress)
        errorView = findViewById(R.id.view_error)
        errorView.hideRetryButtonVisibilty()

        setupBrowseRecycler()
        setupViewListeners()

        viewModel.getFavouriteCity().observe(this, dataStateObserver)
        viewModel.fetchAllFavouriteCity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun setupBrowseRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        cityListAdapter = CityListAdapter { weatherModel -> adapterOnClick(weatherModel) }
        recyclerView.adapter = cityListAdapter
    }

    private val dataStateObserver = Observer<Resource<List<WeatherViewDataModel>>> { resource ->
        when (resource.status) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(resource.data)
            ResourceState.ERROR -> setupScreenForError(resource.message)
        }
    }

    private fun setupScreenForLoadingState() {
        recyclerView.visibility = View.GONE
        progress.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    private fun adapterOnClick(weatherViewDataModel: WeatherViewDataModel) {
        showAlertDialog(weatherViewDataModel)
    }

    private fun setupScreenForSuccess(data: List<WeatherViewDataModel>?) {
        errorView.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
            errorView.visibility = View.VISIBLE
            errorView.setCustomText("Favourite city list is empty...!!")
        }
    }

    private fun updateListView(weatherDataModels: List<WeatherViewDataModel>) {
        cityListAdapter.setFavouriteCityList(weatherDataModels as ArrayList<WeatherViewDataModel>)
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }


    private fun setupViewListeners() {
        view_error.errorListener = errorListener
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            viewModel.getFavouriteCity()
        }
    }

    private fun showAlertDialog(weatherViewDataModel: WeatherViewDataModel) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Do you want to delete this city from your list....!!!")
        alertDialogBuilder.setCancelable(true)

        alertDialogBuilder.setPositiveButton(
            getString(android.R.string.ok)
        )
        { dialog, _ ->
            viewModel.deleteFavouriteCity(weatherViewDataModel.city)
            cityListAdapter.removeItemAndNotify(weatherViewDataModel)
            dialog.cancel()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}



