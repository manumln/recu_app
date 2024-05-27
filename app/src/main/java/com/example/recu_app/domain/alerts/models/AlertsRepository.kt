package com.example.recu_app.domain.alerts.models

import com.example.recu_app.data.alerts.database.entities.AlertEntity
import kotlinx.coroutines.flow.Flow

interface AlertsRepository {
    suspend fun saveAlert(alert: AlertEntity)
    fun getAllAlerts(): Flow<List<AlertEntity>>
    suspend fun deleteAlert(alert: AlertEntity)
}
