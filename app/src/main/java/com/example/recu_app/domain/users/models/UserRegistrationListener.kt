package com.example.recu_app.domain.users.models


interface UserRegistrationListener {
    fun insertarUsuario(registro: RequestRegisterUser?)
}
