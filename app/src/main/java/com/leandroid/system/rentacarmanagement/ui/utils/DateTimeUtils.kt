package com.leandroid.system.rentacarmanagement.ui.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun dateShortFormatString(date: Long, formatOut: String = "dd/MM/yyyy"): String {
        //val parserToDate = SimpleDateFormat(formatIn, Locale.getDefault())
        val formatterString = SimpleDateFormat(formatOut, Locale.getDefault())
        try {
            Calendar.getInstance().apply {
                timeInMillis = date
            }.also {
                return formatterString.format(it.time)
            }
        } catch (e: ParseException) {
            return ""
        }
    }

}