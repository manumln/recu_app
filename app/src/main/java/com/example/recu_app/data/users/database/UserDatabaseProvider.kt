package com.example.recu_app.data.users.database

import android.content.Context
import com.example.recu_app.data.users.database.dao.UserDao
import androidx.room.Room

object UserDatabaseProvider {
    lateinit var database: UserDatabase
    lateinit var userDao: UserDao

    fun init(context: Context) {
        synchronized(this) {
            if (!UserDatabaseProvider::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "my_app_user"
                ).build()
                userDao = database.userDao()
            }
        }
    }
}
