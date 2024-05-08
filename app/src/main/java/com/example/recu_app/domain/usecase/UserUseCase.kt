package com.example.recu_app.domain.usecase

import com.example.recu_app.domain.UserRepository
import com.example.recu_app.domain.models.User
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun login(username: String, password: String): User? {
        return userRepository.login(username, password)
    }

    fun register(user: User) {
        userRepository.registerUser(user)
    }
}