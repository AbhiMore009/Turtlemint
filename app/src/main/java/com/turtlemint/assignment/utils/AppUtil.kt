package com.turtlemint.assignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.ParseException
import java.text.SimpleDateFormat

class AppUtil() {

    companion object {

        fun getDateFormat(date: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
            val parsedDate = inputFormat.parse(date)
            val formattedDate = outputFormat.format(parsedDate)
            return formattedDate
        }


        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false

        }

        @JvmStatic
        @BindingAdapter("formatDate")
        fun TextView.setDate(order_date: String) {
            var outputDate: String? = null
            try {
                val curFormater = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                val postFormater = SimpleDateFormat("MM-dd-yyyy")

                val dateObj = curFormater.parse(order_date)
                outputDate = postFormater.format(dateObj)
                this.setText(outputDate)

            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }
}