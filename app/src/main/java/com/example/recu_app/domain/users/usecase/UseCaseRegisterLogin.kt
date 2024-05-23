package com.example.recu_app.domain.users.usecase

import com.example.recu_app.domain.users.models.RepositoryUsers
import com.example.recu_app.domain.users.models.User

class UseCaseRegisterLogin (val repositoryUsers: RepositoryUsers){

    suspend fun register(user: User): Boolean {
        return repositoryUsers.registerEntity(user)
    }
}