package com.example.recu_app.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlertasApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}