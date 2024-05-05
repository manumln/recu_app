package com.example.recu_app.domain

import com.example.recu_app.domain.models.User
import com.example.recu_app.data.users.mem.service.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {
    fun login(username: String, password: String): User? {
        return userService.login(username, password)
    }

    fun registerUser(user: User) {
        userService.registerUser(user)
    }
}