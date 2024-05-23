package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.RepositoryAlerts

class UseCaseForPosition (val repositoryAlerts: RepositoryAlerts){

    fun devAlert(pos : Int): Alert = repositoryAlerts.devAlertForPos(pos)

}