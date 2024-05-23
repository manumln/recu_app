package com.example.recu_app.domain.alerts.models

import com.example.recu_app.data.users.database.dao.AlertDao
import com.example.recu_app.data.users.database.entities.AlertEntity
import com.example.recu_app.domain.UserDataBaseSingleton
import com.example.recu_app.domain.users.models.Profile

class RepositoryAlerts private constructor(private val alertDao: AlertDao) {

    companion object {
        val repo: RepositoryAlerts by lazy {
            RepositoryAlerts(UserDataBaseSingleton.alertsDao)
        }
    }

    suspend fun addAlertForRepository(newAlert: Alert) {
        val idUser = Profile.profile.user.id
        val alertEntity = AlertEntity(0, idUser, newAlert.textShort, newAlert.message, newAlert.alertDate)
        alertDao.insertAlert(alertEntity)
    }

    suspend fun deleteAlertForRepository(alert: Alert) {
        alertDao.deleteAlertById(alert.id)
    }

    suspend fun showAllAlerts(idUser: Int): List<Alert> {
        return try {
            val listAlertEntity = alertDao.getAlertsForUser(idUser)
            listAlertEntity.map { it.toEntity() }
        } catch (e: Exception) {
            // Manejar la excepción adecuadamente
            emptyList()
        }
    }

    suspend fun showAlertById(id: Int): Alert? {
        return try {
            alertDao.getAlertById(id)?.toEntity()
        } catch (e: Exception) {
            null
        }
    }

    fun AlertEntity.toEntity(): Alert {
        return Alert(
            id = this.id,
            userId = this.userId,
            textShort = this.textShort,
            message = this.message,
            alertDate = this.alertDate
        )
    }

    fun devAlertForPos(pos: Int): Alert? {
        return try {
            ListAlerts.list.alerts.getOrNull(pos)
        } catch (e: Exception) {
            null
        }
    }
}
