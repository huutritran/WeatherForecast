package com.example.weatherforecast.presentation

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecast.R
import com.example.weatherforecast.extension.showAlertDialogAndExitApp
import com.example.weatherforecast.utils.isEmulator
import com.example.weatherforecast.utils.isRooted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onResume() {
        super.onResume()
        if (isRooted() || isEmulator()) {
            showAlertDialogAndExitApp(
                title = getString(R.string.cant_open_app),
                message = getString(R.string.error_device_not_secure)
            )
        }
    }

    override fun onPause() {
        super.onPause()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}