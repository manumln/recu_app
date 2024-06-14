package com.example.recu_app.domain.users.models.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseLoginUser(
    @SerializedName("result")
    @Expose
    val result: String,

    @SerializedName("token")
    @Expose
    val token : String,

    @SerializedName("id")
    @Expose
    val id : Int,

    @SerializedName("nombre")
    @Expose
    val name : String,

    @SerializedName("email")
    @Expose
    val email : String,

    @SerializedName("telefono")
    @Expose
    val phone : String
) {}