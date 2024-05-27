package com.example.recu_app.data.alerts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recu_app.data.alerts.database.dao.AlertsDao
import com.example.recu_app.data.alerts.database.entities.AlertEntity

@Database(
    entities = [AlertEntity::class],
    version = 1,
)
abstract class AlertsDatabase : RoomDatabase() {

    abstract fun dao(): AlertsDao

}
