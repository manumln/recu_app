package com.example.recu_app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recu_app.domain.alerts.models.Alert

class DetailsAlertViewModel : ViewModel() {
    val alertLiveData = MutableLiveData<Alert>()

    fun setAlert(alert: Alert) {
        alertLiveData.value = alert
    }
}
