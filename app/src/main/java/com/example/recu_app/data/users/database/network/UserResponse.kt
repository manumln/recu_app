package com.example.recu_app.data.users.database.network

import com.example.recu_app.domain.users.models.User

data class UserResponse(
    val result: String,
    val message: String,
    val usuarios: List<User>
)
