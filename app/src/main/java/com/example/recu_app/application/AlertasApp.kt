package com.example.recu_app.application

import android.app.Application
import com.example.recu_app.data.users.database.UserDatabaseProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlertasApp : Application() {

    override fun onCreate() {
        super.onCreate()
        UserDatabaseProvider.init(this)
    }
}