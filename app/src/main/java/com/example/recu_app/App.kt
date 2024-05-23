package com.example.recu_app

import android.app.Application
import com.example.recu_app.domain.UserDataBaseSingleton

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UserDataBaseSingleton.init(this)//tengo que pasarle el contexto, para que se cree.
    }


}