package com.example.recu_app.ui.viewmodel.perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recu_app.domain.users.models.UserDetailsRepository
import com.example.recu_app.domain.users.models.User

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserDetailsRepository = UserDetailsRepository(application)
    private val allUserData: LiveData<List<User>>? = repository.getAllData()

    fun getAllUserData(): LiveData<List<User>>? {
        return allUserData
    }
}
