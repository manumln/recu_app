package com.example.recu_app.domain.users.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("telefono")
    @Expose
    var telefono: String? = null,

    @SerializedName("nombre")
    @Expose
    var nombre: String? = null,

    @SerializedName("token")
    @Expose
    var token: String? = null
)
