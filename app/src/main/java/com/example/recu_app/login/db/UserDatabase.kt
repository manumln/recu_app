package com.example.recu_app.login.db

import android.content.Context
import androidx.room.Database
import com.example.recu_app.login.dao.DaoAccess
import androidx.room.RoomDatabase
import com.example.recu_app.login.modelorentity.User
import androidx.room.Room

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {


    abstract fun daoAccess(): DaoAccess

    companion object {

        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase? {

            if (INSTANCE == null) synchronized(UserDatabase::class.java) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                        context, UserDatabase::class.java, "USER_DATABASE"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                }

            }

            return INSTANCE

        }
    }
}