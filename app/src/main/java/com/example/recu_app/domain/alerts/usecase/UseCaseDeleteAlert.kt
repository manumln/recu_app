package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.ListAlerts
import com.example.recu_app.domain.alerts.models.RepositoryAlerts

class UseCaseDeleteAlert(private val repo: RepositoryAlerts) {
    suspend fun delete(alert: Alert): Int {
        repo.deleteAlert(alert)
        val pos = ListAlerts.list.alerts.indexOf(alert)
        ListAlerts.list.alerts.removeAt(pos)
        return pos
    }
}
