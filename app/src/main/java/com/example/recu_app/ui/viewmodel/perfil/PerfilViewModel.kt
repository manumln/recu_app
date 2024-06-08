package com.example.recu_app.ui.viewmodel.perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recu_app.domain.users.models.UserDetailsRepository
import com.example.recu_app.domain.users.models.UserEntity

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserDetailsRepository = UserDetailsRepository(application)

    fun getAllUserData(): LiveData<List<UserEntity>>? {
        return repository.getAllData()
    }

    fun getUserById(userId: Int): LiveData<UserEntity>? {
        return repository.getUserById(userId)
    }

}
