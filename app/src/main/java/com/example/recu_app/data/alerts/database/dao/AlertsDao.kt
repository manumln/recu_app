package com.example.recu_app.data.alerts.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertsDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveAlert(alert: AlertEntity)

    @Delete
    suspend fun deleteAlert(alert: AlertEntity)

    @Query("SELECT * from alerts")
    fun getAlerts(): Flow<List<AlertEntity>>
}
