package com.turtlemint.assignment.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.ParseException
import java.text.SimpleDateFormat

class AppUtil {

    companion object {
        fun getDateFormat(date: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
            val parsedDate = inputFormat.parse(date)
            val formattedDate = outputFormat.format(parsedDate)
            return formattedDate
        }

        @JvmStatic // add this line !!
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