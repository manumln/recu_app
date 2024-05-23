package com.example.recu_app

import android.app.Application
import com.example.recu_app.domain.UserDataBaseSingleton

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UserDataBaseSingleton.init(this)
    }
}
