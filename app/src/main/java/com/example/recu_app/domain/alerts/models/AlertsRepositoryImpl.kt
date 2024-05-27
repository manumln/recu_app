package com.example.recu_app.domain.alerts.models

import com.example.recu_app.data.alerts.database.dao.AlertsDao
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.domain.alerts.models.AlertsRepository
import javax.inject.Inject

class AlertsRepositoryImpl @Inject constructor(
    private val dao: AlertsDao
) : AlertsRepository {
    override suspend fun saveAlert(alert: AlertEntity) = dao.saveAlert(alert)
    override fun getAllAlerts() = dao.getAlerts()
    override suspend fun deleteAlert(alert: AlertEntity) = dao.deleteAlert(alert)
}
