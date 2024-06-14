package com.example.recu_app.domain.users.models.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseRegisterUser(
    @SerializedName("result")
    @Expose
    val result: String,

    @SerializedName("id")
    @Expose
    val id : Int
)   {


}