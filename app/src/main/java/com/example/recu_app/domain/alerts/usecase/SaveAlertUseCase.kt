package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.domain.alerts.models.AlertsRepository
import javax.inject.Inject

class SaveAlertUseCase @Inject constructor(
    private val repository: AlertsRepository
) {
    suspend operator fun invoke(alert: AlertEntity) {
        repository.saveAlert(alert)
    }
}