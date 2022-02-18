package com.leandroid.system.rentacarmanagement.ui.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

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
}
