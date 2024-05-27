package com.example.recu_app.ui.viewmodel.users

import androidx.lifecycle.LiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recu_app.domain.users.models.UserDetailsRepository
import com.example.recu_app.domain.users.models.User

 class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: UserDetailsRepository
     private var getAllDatas: LiveData<List<User>>

init {

    repository = UserDetailsRepository(application)
    getAllDatas = repository.getAllData()!!
}

    fun insert(data: User) {
        repository.insertData(data)
    }
    fun getGetAllData(): LiveData<List<User>> {
        return getAllDatas
    }
}
