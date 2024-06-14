package com.example.recu_app.ui.viewmodel.perfil

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recu_app.domain.users.models.User

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val userProfileLiveData = MutableLiveData<User?>()
    val userProfile: LiveData<User?> get() = userProfileLiveData

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val userEmail = sharedPreferences.getString("email", null)
        val userName = sharedPreferences.getString("name", null)
        val userPhone = sharedPreferences.getString("phone", null)

        if (!userEmail.isNullOrBlank() && !userName.isNullOrBlank()) {
            val user = User(
                email = userEmail,
                nombre = userName,
                telefono = userPhone
            )
            userProfileLiveData.value = user
        } else {
            userProfileLiveData.value = null
        }
    }
}
