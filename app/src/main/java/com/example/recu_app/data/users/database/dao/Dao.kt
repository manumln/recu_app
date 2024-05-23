package com.example.recu_app.data.users.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recu_app.data.users.database.entities.AlertEntity
import com.example.recu_app.data.users.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM tblusers WHERE email = :email and password =:password")
    suspend fun login(email: String, password: String): UserEntity



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg user : UserEntity)

}

@Dao
interface AlertDao {
    @Insert
    suspend fun insertAlert(alert: AlertEntity)

    @Query("DELETE FROM alerts WHERE id = :alertId")
    suspend fun deleteAlertById(alertId: Int)

    @Query("SELECT * FROM alerts WHERE userId = :userId")
    suspend fun getAlertsForUser(userId: Int): List<AlertEntity>

    @Query("SELECT * FROM alerts WHERE id = :alertId")
    suspend fun getAlertById(alertId: Int): AlertEntity?

    // Nuevo método para obtener todas las alertas
    @Query("SELECT * FROM alerts")
    suspend fun getAllAlerts(): List<AlertEntity>
}
