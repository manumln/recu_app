package com.example.recu_app.ui.viewmodel.perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recu_app.domain.users.models.UserDetailsRepository
import com.example.recu_app.domain.users.models.UserEntity

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserDetailsRepository = UserDetailsRepository(application)
    private val allUserDataEntity: LiveData<List<UserEntity>>? = repository.getAllData()

    fun getAllUserData(): LiveData<List<UserEntity>>? {
        return allUserDataEntity
    }
}
