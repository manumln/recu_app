package com.example.recu_app.domain.users.models.Request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestLoginUser {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}
