package com.example.recu_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recu_app.domain.models.User
import com.example.recu_app.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    fun login(username: String, password: String): Boolean {
        val loggedInUser = userUseCase.login(username, password)
        return loggedInUser != null
    }

    fun registerUser(user: User) {
        userUseCase.register(user)
    }
}
