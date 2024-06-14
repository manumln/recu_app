package com.example.recu_app.ui.viewmodel.perfil

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recu_app.domain.users.models.User

class PerfilViewModel(app: Application) : AndroidViewModel(app) {

    private val userLiveData = MutableLiveData<User?>()
    val userData: LiveData<User?> get() = userLiveData

    private val prefs: SharedPreferences = app.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val userEmail = prefs.getString("email", null)
        val userName = prefs.getString("name", null)
        val userPhone = prefs.getString("phone", null)


        if (!userEmail.isNullOrBlank() && !userName.isNullOrBlank()) {
            val user = User().apply {
                email = userEmail
                nombre = userName
                telefono = userPhone
            }
            userLiveData.value = user
        } else {
            userLiveData.value = null
        }
    }
}
