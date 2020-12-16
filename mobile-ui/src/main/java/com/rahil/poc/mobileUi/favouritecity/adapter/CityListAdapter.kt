package com.rahil.poc.mobileUi.favouritecity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rahil.poc.R
import com.rahil.poc.mobileUi.favouritecity.listener.OnItemLongClickListener
import com.rahil.poc.mobileUi.utils.Constants
import com.rahil.poc.mobileUi.utils.DateUtil
import com.rahil.poc.presentation.model.WeatherViewDataModel
import java.util.*
import kotlin.collections.ArrayList


class CityListAdapter(private val onClick: (WeatherViewDataModel) -> Unit) :
    RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private var mCityList: ArrayList<WeatherViewDataModel> = ArrayList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_list_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mCityList[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int
            = mCityList.size

    inner class ViewHolder(
        mView: View,
        onClick: (WeatherViewDataModel) -> Unit
    ) : RecyclerView.ViewHolder(mView) {
        private val mCurrentTime: TextView = mView.findViewById(R.id.currentTime)
        private val mCity: TextView = mView.findViewById(R.id.city)
        private val mTemperature: TextView = mView.findViewById(R.id.temp)
        private val mTempImage: ImageView = mView.findViewById(R.id.temp_image)
        private val mMinTemperature: TextView = mView.findViewById(R.id.mintemp)
        private val mMaxTemperature: TextView = mView.findViewById(R.id.maxtemp)
        private val mHumidity: TextView = mView.findViewById(R.id.humidity)
        private val mPressure: TextView = mView.findViewById(R.id.pressure)
        private val mSunrise: TextView = mView.findViewById(R.id.sunrise)
        private val mSunset: TextView = mView.findViewById(R.id.sunset)
        private var weatherViewDataModel: WeatherViewDataModel? = null

        init {
            itemView.setOnLongClickListener() {
                weatherViewDataModel?.let {
                    onClick(it)
                }
                return@setOnLongClickListener true
            }

        }

        fun bindData(item: WeatherViewDataModel) {
            weatherViewDataModel = item
            Glide
                .with(context)
                .load(Constants.OPEN_WEATHER_IMAGE_API +  item.icon + Constants.PNG_EXTENSION)
                .apply(RequestOptions().override(200, 200).centerCrop())
                .into(mTempImage);
            mCurrentTime.setText(DateUtil.formatDateToDisplay(Calendar.getInstance().getTime()));
            mCity.text = item.city
            mTemperature.text = context.getString(R.string.temp_string,  (item.temp).toString())
            mMinTemperature.text = context.getString(R.string.min_temp,  (item.temp_min).toString())
            mMaxTemperature.text = context.getString(R.string.max_temp,  (item.temp_max).toString())
            mHumidity.text = context.getString(R.string.humidity, (item.humidity).toString())
            mPressure.text = context.getString(R.string.pressure, (item.pressure).toString())
            mSunrise.text = context.getString(R.string.sunrise, (DateUtil.unixTimeToFormatTime(context, item.sunrise.toLong())))
            mSunset.text = context.getString(R.string.sunset,(DateUtil.unixTimeToFormatTime(context, item.sunrise.toLong())))
        }

    }

    fun setFavouriteCityList(cityList: ArrayList<WeatherViewDataModel>) {
        this.mCityList = cityList
        notifyDataSetChanged()
    }

    fun removeItemAndNotify(weatherViewDataModel: WeatherViewDataModel) {
        if (!mCityList.isEmpty()) {
            val index =  mCityList.indexOf(weatherViewDataModel)
            mCityList.removeAt(index)
            notifyItemRemoved(index)
        }

    }
}