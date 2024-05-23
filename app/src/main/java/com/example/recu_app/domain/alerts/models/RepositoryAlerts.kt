package com.example.recu_app.domain.alerts.models

import com.example.recu_app.data.users.database.dao.AlertDao
import com.example.recu_app.data.users.database.entities.AlertEntity
import com.example.recu_app.domain.UserDataBaseSingleton

class RepositoryAlerts private constructor(private val alertDao: AlertDao) {
    companion object {
        val repo: RepositoryAlerts by lazy {
            RepositoryAlerts(UserDataBaseSingleton.alertsDao)
        }
    }

    suspend fun addAlert(alert: Alert) {
        val alertEntity = AlertEntity(
            id = alert.id,
            userId = alert.userId,
            name = alert.name,
            textShort = alert.textShort,
            message = alert.message,
            alertDate = alert.alertDate
        )
        alertDao.insertAlert(alertEntity)
    }

    suspend fun deleteAlert(alert: Alert) {
        alertDao.deleteAlertById(alert.id)
    }

    suspend fun getAlertsForUser(userId: Int): List<Alert> {
        val alertEntities = alertDao.getAlertsForUser(userId)
        return alertEntities.map { it.toAlert() }
    }

    suspend fun getAlertById(alertId: Int): Alert? {
        val alertEntity = alertDao.getAlertById(alertId)
        return alertEntity?.toAlert()
    }

    // Nueva función para obtener todas las alertas
    suspend fun getAllAlerts(): List<Alert> {
        val alertEntities = alertDao.getAllAlerts()
        return alertEntities.map { it.toAlert() }
    }

    // Función para obtener alerta por posición
    suspend fun getAlertByPosition(pos: Int): Alert? {
        val alerts = getAllAlerts()
        return if (pos in alerts.indices) alerts[pos] else null
    }

    private fun AlertEntity.toAlert(): Alert {
        return Alert(
            id = this.id,
            userId = this.userId,
            name = this.name,
            textShort = this.textShort,
            message = this.message,
            alertDate = this.alertDate
        )
    }
}
