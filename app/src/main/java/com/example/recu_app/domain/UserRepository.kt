package com.example.recu_app.domain

import com.example.recu_app.domain.models.User
import com.example.recu_app.data.users.mem.service.UserService

class UserRepository {

    fun login(username: String, password: String): User? {
        return UserService.login(username, password)
    }

    fun registerUser(user: User) {
        UserService.registerUser(user)
    }
}