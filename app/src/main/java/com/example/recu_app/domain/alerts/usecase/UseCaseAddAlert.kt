package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.ListAlerts
import com.example.recu_app.domain.alerts.models.RepositoryAlerts

class UseCaseAddAlert(private val repo: RepositoryAlerts) {
    suspend fun add(alert: Alert): Int {
        repo.addAlert(alert)
        return ListAlerts.list.alerts.lastIndex
    }
}
