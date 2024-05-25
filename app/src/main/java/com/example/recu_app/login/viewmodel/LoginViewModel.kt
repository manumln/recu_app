package com.manumln.alertas.login.viewmodel

import androidx.lifecycle.LiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recu_app.login.Repository.UserDetailsRepository
import com.example.recu_app.login.modelorentity.User


/**
 * Created by Akash Kumar on 4/17/2020.
 * https://github.com/eduxcellence
 * akkr2017@gmail.com
 */

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
