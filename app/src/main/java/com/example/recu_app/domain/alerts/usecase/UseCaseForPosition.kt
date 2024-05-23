package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.RepositoryAlerts

class UseCaseForPosition(private val repositoryAlerts: RepositoryAlerts) {

    suspend fun devAlert(pos: Int): Alert? = repositoryAlerts.getAlertByPosition(pos)

}
