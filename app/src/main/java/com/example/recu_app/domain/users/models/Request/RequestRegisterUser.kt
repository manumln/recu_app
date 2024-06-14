package com.example.recu_app.domain.users.models.Request

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

    @SerializedName("telefono")
    @Expose
    var telefono: String? = null

}
