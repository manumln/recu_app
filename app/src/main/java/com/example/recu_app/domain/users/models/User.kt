package com.example.recu_app.domain.users.models

data class User(
    var id: Int,
    var name: String,
    var email: String,
    var passw: String,
    var phone: String,
    var imagen: String
) {
    constructor(email: String, passw: String) : this(0, "", email, passw, "", "")
}
