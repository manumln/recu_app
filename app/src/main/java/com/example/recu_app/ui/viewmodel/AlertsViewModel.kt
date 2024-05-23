package com.example.recu_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.ListAlerts
import com.example.recu_app.domain.alerts.models.RepositoryAlerts
import com.example.recu_app.domain.alerts.usecase.UseCaseAddAlert
import com.example.recu_app.domain.alerts.usecase.UseCaseDeleteAlert
import com.example.recu_app.domain.alerts.usecase.UseCaseShowAlerts
import com.example.recu_app.domain.users.models.Profile
import kotlinx.coroutines.launch

class AlertsViewModel : ViewModel() {
    private val repo = RepositoryAlerts.repo
    private val useCaseAddAlert = UseCaseAddAlert(repo)
    private val useCaseDeleteAlert = UseCaseDeleteAlert(repo)
    private val useCaseShowAlerts = UseCaseShowAlerts(repo)

    private val _listAlertsLiveData = MutableLiveData<List<Alert>>()
    val listAlertsLiveData: LiveData<List<Alert>> get() = _listAlertsLiveData

    private val _posNewAlertLiveData = MutableLiveData<Int>()
    val posNewAlertLiveData: LiveData<Int> get() = _posNewAlertLiveData

    private val _posDeleteAlertLiveData = MutableLiveData<Int>()
    val posDeleteAlertLiveData: LiveData<Int> get() = _posDeleteAlertLiveData

    init {
        loadAlerts()
    }

    private fun loadAlerts() {
        viewModelScope.launch {
            val userId = Profile.profile.user.id
            val alerts = useCaseShowAlerts.showAlerts(userId)
            _listAlertsLiveData.value = alerts
        }
    }

    fun addAlert(alert: Alert) {
        viewModelScope.launch {
            useCaseAddAlert.add(alert)
            loadAlerts()  // Reload to get updated list
        }
    }

    fun deleteAlert(pos: Int) {
        viewModelScope.launch {
            val alert = _listAlertsLiveData.value?.get(pos)
            if (alert != null) {
                useCaseDeleteAlert.delete(alert)
                loadAlerts()  // Reload to get updated list
            }
        }
    }
}
