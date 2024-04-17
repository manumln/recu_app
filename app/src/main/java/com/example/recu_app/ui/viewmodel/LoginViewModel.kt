package com.example.recu_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recu_app.domain.models.User
import com.example.recu_app.domain.usecase.UserUseCase

class LoginViewModel : ViewModel() {

    private lateinit var userUseCase: UserUseCase

    fun setUserUseCase(userUseCase: UserUseCase) {
        this.userUseCase = userUseCase
    }

    fun login(username: String, password: String): Boolean {
        val loggedInUser = userUseCase.login(username, password)
        return loggedInUser != null
    }

    fun registerUser(user: User) {
        userUseCase.register(user)
    }
}
