package com.example.recu_app.data.users.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recu_app.data.alerts.database.dao.AlertsDao
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.data.users.database.dao.UserDao
import com.example.recu_app.data.users.database.entities.UserEntity
import com.example.recu_app.utils.DateConverter


@Database(
    entities = [UserEntity::class,
        AlertEntity::class],
    version = 1

)

@TypeConverters(DateConverter::class)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun alertsDao(): AlertsDao



}
