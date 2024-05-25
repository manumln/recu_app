package com.example.recu_app.ui.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recu_app.login.modelorentity.User

class PerfilViewModel : ViewModel() {
    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User> get() = _userDetails

    fun setUserDetails(user: User) {
        _userDetails.value = user
    }
}
