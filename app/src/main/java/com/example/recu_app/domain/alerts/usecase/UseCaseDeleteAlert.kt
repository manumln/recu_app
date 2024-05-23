package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.ListAlerts
import com.example.recu_app.domain.alerts.models.RepositoryAlerts

class UseCaseDeleteAlert(val repo : RepositoryAlerts) {

    suspend fun delete(alert: Alert){
        repo.deleteAlertForRepository(alert)
        ListAlerts.list.alerts.remove(alert) // También lo elimino de la caché.
    }
}
