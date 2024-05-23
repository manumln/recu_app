package com.example.recu_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.domain.alerts.models.ListAlerts
import com.example.recu_app.domain.alerts.models.RepositoryAlerts
import com.example.recu_app.domain.alerts.usecase.UseCaseAddAlert
import com.example.recu_app.domain.alerts.usecase.UseCaseForPosition
import com.example.recu_app.domain.alerts.usecase.UseCaseShowAlerts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlertsViewModel : ViewModel() {
    var posNewAlertLiveDate = MutableLiveData<Int> () //Notifico un cambio al añadir una alerta
    var posDeleteAlertLiveData = MutableLiveData<Int> () //Notifico un cambio al eliminar una alerta
    var listAlertsLiveData = MutableLiveData<List<Alert>>()  //Será mi lista de alertas.

    private val useCaseShowAlerts = UseCaseShowAlerts(RepositoryAlerts.repo)
    private val useCaseAddAlert = UseCaseAddAlert(RepositoryAlerts.repo)
    private val useCaseForPosition = UseCaseForPosition(RepositoryAlerts.repo)
    /*private val _text = MutableLiveData<String>().apply {
        value = "Alertas"
    }
    val text : LiveData<String> = _text  //recordamos que LiveData es una clase Abstracta.

*/


    fun showAlerts(){
        //Queremos obtener las alertas.
        viewModelScope.launch(Dispatchers.IO) {
            ListAlerts.list.alerts = useCaseShowAlerts.showAlerts() //Cargo los datos en cache
            ListAlerts.list.alerts.let {
                withContext(Dispatchers.Main) {
                    listAlertsLiveData.value = it
                }
            }
        }
    }


    fun addAlerts(newAlert: Alert){
        viewModelScope.launch(Dispatchers.IO) {
            val pos = useCaseAddAlert.add(newAlert)  //He añadido en la BBDD y en cache
            withContext(Dispatchers.Main) {
                posNewAlertLiveDate.value = pos  //notificamos de la posición de la nueva alerta.
                //  listAlertsLiveData.value = ListAlerts.list.alerts
            }
        }

    }



    fun delAlert(pos: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // Eliminar alerta en la BBDD y en caché
            RepositoryAlerts.repo.devAlertForPos(pos)
            ListAlerts.list.alerts.removeAt(pos)
            withContext(Dispatchers.Main) {
                posDeleteAlertLiveData.value = pos
            }
        }
    }




    //Devuelve la alerta en posición de la lista en memoria.
    fun getAlertForPosition(pos: Int) : Alert = useCaseForPosition.devAlert(pos)

}