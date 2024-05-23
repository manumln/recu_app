package com.example.recu_app.domain.users.usecase

import com.example.recu_app.domain.users.models.RepositoryUsers
import com.example.recu_app.domain.users.models.User

class UseCaseLogin (val repositoryUsers: RepositoryUsers){


    suspend fun login(email: String, password: String): User?{
        return repositoryUsers.isLoginEntity(email, password)
    }

}