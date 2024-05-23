package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.ListAlerts
import com.example.recu_app.domain.alerts.models.RepositoryAlerts

class UseCaseAddAlert(val repo : RepositoryAlerts) {

    suspend fun add(alert: Alert):Int{
        repo.addAlertForRepository(alert)
        ListAlerts.list.alerts.add(alert)//También actualizo en cache.
        return ListAlerts.list.alerts.lastIndex  //devuelvo la última posición insertada.

    }


}