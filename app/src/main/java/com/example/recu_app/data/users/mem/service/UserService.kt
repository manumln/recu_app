package com.example.recu_app.data.users.mem.service

import com.example.recu_app.domain.models.User
import com.example.recu_app.data.users.mem.objects.RepositoryObject

object UserService {

    fun login(username: String, password: String): User? {
        val users = RepositoryObject.getAllUsers()
        return users.find { it.username == username && it.password == password }
    }

    fun registerUser(user: User) {
        RepositoryObject.addUser(user)
    }

}
