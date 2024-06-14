package com.example.recu_app.domain.users.models.listener

import com.example.recu_app.domain.users.models.Request.RequestRegisterUser

interface UserRegistrationListener {
    fun insertUser(registro: RequestRegisterUser?)
}
