package com.example.recu_app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.ListAlerts

class DetailsAlertViewModel : ViewModel() {
    var alertLiveData = MutableLiveData<Alert>()  //Para notificar que ya he recuperado la alerta.

    fun devAlertForPos(pos: Int): Alert {
        return ListAlerts.list.alerts[pos]
    }

}