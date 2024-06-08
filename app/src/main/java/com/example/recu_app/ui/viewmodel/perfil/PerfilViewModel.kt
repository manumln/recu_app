package com.example.recu_app.ui.viewmodel.perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recu_app.domain.users.models.UserDetailsRepository
import com.example.recu_app.domain.users.models.UserEntity

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: UserDetailsRepository = UserDetailsRepository(application)
    private var userId: Int = -1

    fun init(userId: Int) {
        this.userId = userId
    }

    fun getUserById(): LiveData<UserEntity>? {
        return repository.getUserById(userId)
    }
}
