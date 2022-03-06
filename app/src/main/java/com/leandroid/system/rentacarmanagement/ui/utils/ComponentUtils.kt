package com.leandroid.system.rentacarmanagement.ui.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.leandroid.system.rentacarmanagement.ui.booking.BookingDialogFragment

object ComponentUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showDialog(
        context: Context,
        message: String,
        positiveButtonTitle: String?,
        cancelButtonTitle: String?,
        positiveAction: () -> Unit
    ) {
        val alert = AlertDialog.Builder(context).apply {
            setMessage(message)
        }
        if (!positiveButtonTitle.isNullOrBlank()) {
            alert.setPositiveButton(positiveButtonTitle,
                DialogInterface.OnClickListener { dialog, id ->
                    positiveAction()
                })
        }
        if (!cancelButtonTitle.isNullOrBlank()) {
            alert.setNegativeButton(cancelButtonTitle,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        }

        alert.create().show()
    }

    fun getRangePicker(title: String, startDate: Long, endDate: Long) =
        MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(title)
            .setSelection(
                Pair(
                    startDate,
                    endDate
                )
            )
            .build()

    fun getTimePicker(title: String, timeFormat: Int = TimeFormat.CLOCK_24H) =
        MaterialTimePicker.Builder()
            .setTimeFormat(timeFormat)
            .setTitleText(title)
            .build()

}
