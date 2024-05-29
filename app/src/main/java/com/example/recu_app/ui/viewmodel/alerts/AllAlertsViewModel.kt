package com.example.recu_app.ui.viewmodel.alerts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.data.users.database.converters.Resource
import com.example.recu_app.domain.alerts.usecase.DeleteAlertUseCase
import com.example.recu_app.domain.alerts.usecase.GetAllAlertsUseCase
import com.example.recu_app.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllAlertsViewModel @Inject constructor(
    private val getAllAlertsUseCase: GetAllAlertsUseCase,
    private val deleteAlertUseCase: DeleteAlertUseCase,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {

    private val _alerts = MutableLiveData<Resource<List<AlertEntity>>>()
    val alerts: LiveData<Resource<List<AlertEntity>>> = _alerts

    fun getAlerts() {
        viewModelScope.launch(dispatchers.main) {
            _alerts.postValue(Resource.Loading())
            getAllAlertsUseCase().collect {
                _alerts.value = Resource.Success(it)
            }
        }
    }

    fun deleteAlert(alert: AlertEntity) {
        viewModelScope.launch(dispatchers.main) {
            deleteAlertUseCase(alert)
        }
    }
}
