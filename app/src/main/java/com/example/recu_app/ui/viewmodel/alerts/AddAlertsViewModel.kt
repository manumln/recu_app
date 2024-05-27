package com.example.recu_app.ui.viewmodel.alerts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.domain.alerts.models.AlertsRepository
import com.example.recu_app.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAlertsViewModel @Inject constructor(
    private val repository: AlertsRepository,
    private val dispatchers: DispatchersProvider,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    fun saveAlert(alert: AlertEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.saveAlert(alert)
        }
    }
}
