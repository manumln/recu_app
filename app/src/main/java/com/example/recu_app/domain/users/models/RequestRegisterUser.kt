package com.example.recu_app.domain.users.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestRegisterUser {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("nombre")
    @Expose
    var nombre: String? = null

    @SerializedName("imagen")
    @Expose
    var imagen: String? = null

    @SerializedName("disponible")
    @Expose
    var disponible = 0
}
