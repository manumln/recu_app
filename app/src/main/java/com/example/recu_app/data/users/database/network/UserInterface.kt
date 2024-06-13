package com.example.recu_app.data.users.database.network

import com.example.recu_app.data.users.database.network.UserResponse
import com.example.recu_app.domain.users.models.RequestLoginUser
import com.example.recu_app.domain.users.models.RequestRegisterUser
import com.example.recu_app.domain.users.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserInterface {
    @Headers("Content-Type: application/json")
    @POST("auth")
    suspend fun login(@Body login: RequestLoginUser?): Response<User?>

    @Headers("Content-Type: application/json")
    @POST("registro")
    suspend fun registro(@Body registro: RequestRegisterUser?): Response<User?>

    @GET("user")
    suspend fun getUsers(@Header("api-key") token: String?): Response<UserResponse?>
}