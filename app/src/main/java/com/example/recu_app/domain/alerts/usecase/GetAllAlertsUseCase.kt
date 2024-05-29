package com.example.recu_app.domain.alerts.usecase

import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.domain.alerts.models.AlertsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAlertsUseCase @Inject constructor(
    private val repository: AlertsRepository
) {
    operator fun invoke(): Flow<List<AlertEntity>> {
        return repository.getAllAlerts()
    }
}