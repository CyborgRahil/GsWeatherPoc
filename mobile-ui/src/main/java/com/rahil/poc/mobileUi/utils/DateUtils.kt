package com.rahil.poc.mobileUi.utils

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    /*
    Displays date in E, MMM dd h:m a format
     */
    fun formatDateToDisplay(date: Date?): String {
        val sdf = SimpleDateFormat("E, MMM dd h:mm a", Locale.US)
        return sdf.format(date)
    }

    /*
    Returns relative Time
     */
    fun getLastUpdated(dateinMillis: Long): CharSequence {
        val currentTime: Long =
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis()
        return DateUtils.getRelativeTimeSpanString(
            dateinMillis * 1000L, currentTime,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_NO_NOON
        )
    }

    /*
       Returns hour and min using time in millis
     */
    fun getHourMinsTime(dateinMillis: Long): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(dateinMillis * 1000)
        return calendar.get(Calendar.HOUR).toString() + ":" + calendar.get(Calendar.MINUTE)
    }

    fun unixTimeToFormatTime(context: Context?, unixTime: Long): String? {
        val unixTimeToMillis = unixTime * 1000
        val date = Date(unixTimeToMillis)
        val format =
            SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy a")
        format.timeZone = TimeZone.getTimeZone("GMT")
        return format.format(date)
    }
}