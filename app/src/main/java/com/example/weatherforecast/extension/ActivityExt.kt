package com.example.weatherforecast.extension

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.weatherforecast.R


fun Activity.showAlertDialogAndExitApp(title: String, message: String?) {
    AlertDialog.Builder(
        this,
        android.R.style.Theme_DeviceDefault_Light_NoActionBar
    ).create().apply {
        setTitle(title)
        setMessage(message)
        setCancelable(false)
        setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
    }.also { it.show() }
}