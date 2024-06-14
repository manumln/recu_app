package com.example.recu_app.application

import android.app.Application
import androidx.room.Room
import com.example.recu_app.data.users.database.UserDatabase // Asegúrate de importar la clase correcta de tu base de datos
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlertasApp : Application() {

    companion object {
        lateinit var database: UserDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "my_app_user"
        ).build()
    }
}
