package com.example.recu_app.ui.viewmodel.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recu_app.domain.users.models.UserDetailsRepository
import com.example.recu_app.domain.users.models.UserEntity

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserDetailsRepository = UserDetailsRepository(application)
    private val allUsers: LiveData<List<UserEntity>> = repository.getAllData()!!

    fun getAllUsers(): LiveData<List<UserEntity>> {
        return allUsers
    }

    fun insert(userEntity: UserEntity) {
        repository.insertData(userEntity)
    }
}
