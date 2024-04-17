package com.example.recu_app.data.users.mem.objects

import com.example.recu_app.domain.models.User

object RepositoryObject {
    private val usuarios: MutableList<User> = mutableListOf()

    init {
        usuarios.add(User("manu", "manu@email.com", "1234"))
        usuarios.add(User("daniela", "daniela@email.com", "1234"))
        usuarios.add(User("victor", "victor@email.com", "1234"))
    }

    fun addUser(user: User) {
        usuarios.add(user)
    }

    fun getAllUsers(): List<User> {
        return usuarios.toList()
    }

}