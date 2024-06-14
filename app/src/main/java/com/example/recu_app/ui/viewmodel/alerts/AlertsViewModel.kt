package com.example.recu_app.ui.viewmodel.alerts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.utils.converters.Resource
import com.example.recu_app.domain.alerts.models.AlertsRepository
import com.example.recu_app.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val repository: AlertsRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {

    private val _alerts = MutableLiveData<Resource<List<AlertEntity>>>()
    val alerts: LiveData<Resource<List<AlertEntity>>> = _alerts

    fun getAlerts() {
        viewModelScope.launch(dispatchers.main) {
            _alerts.postValue(Resource.Loading())
            repository.getAllAlerts().collect {
                _alerts.value = Resource.Success(it)
            }
        }
    }

    fun createOrUpdateAlert(alert: AlertEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.saveAlert(alert)
        }
    }

    fun deleteAlert(alert: AlertEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.deleteAlert(alert)
        }
    }
}
