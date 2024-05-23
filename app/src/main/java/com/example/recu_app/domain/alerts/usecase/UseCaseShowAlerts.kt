package com.example.recu_app.domain.alerts.usecase

import android.provider.ContactsContract
import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.RepositoryAlerts

class UseCaseShowAlerts(private val repositoryAlerts: RepositoryAlerts) {
    suspend fun showAlerts(userId: Int): List<Alert> {
        return repositoryAlerts.getAlertsForUser(userId).toMutableList()
    }
}
