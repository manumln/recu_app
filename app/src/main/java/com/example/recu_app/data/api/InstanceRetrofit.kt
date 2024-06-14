package com.example.recu_app.data.api

import com.example.recu_app.data.users.database.network.UserInterfaceApi
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanceRetrofit {
    private const val BASE_URL = "http://10.0.2.2/api-alertas/endp/"

    private val gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getUsers(): UserInterfaceApi {
        return retrofit.create(com.example.recu_app.data.users.database.network.UserInterfaceApi::class.java)
    }

}
