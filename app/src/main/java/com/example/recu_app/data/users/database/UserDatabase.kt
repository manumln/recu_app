package com.example.recu_app.data.users.database

import android.content.Context
import androidx.room.Database
import com.example.recu_app.data.users.database.dao.UserDao
import androidx.room.RoomDatabase
import com.example.recu_app.domain.users.models.UserEntity
import androidx.room.Room

@Database(entities = [UserEntity::class], version = 4, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun daoAccess(): UserDao

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