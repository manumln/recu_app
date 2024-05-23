package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.domain.alerts.models.RepositoryAlerts
import com.example.recu_app.domain.users.models.Profile

class UseCaseShowAlerts (val repositoryAlerts: RepositoryAlerts) {
    suspend fun showAlerts() = repositoryAlerts.showAllAlerts(Profile.profile.user.id).toMutableList()


}