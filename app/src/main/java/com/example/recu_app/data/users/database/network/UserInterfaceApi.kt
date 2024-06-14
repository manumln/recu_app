package com.example.recu_app.data.users.database.network

import com.example.recu_app.domain.users.models.Request.RequestLoginUser
import com.example.recu_app.domain.users.models.Request.RequestRegisterUser
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.data.users.database.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserInterfaceApi {
    @Headers("Content-Type: application/json")
    @POST("auth")
    suspend fun login(@Body login: RequestLoginUser?): Response<User?>

    @Headers("Content-Type: application/json")
    @POST("registro")
    suspend fun registro(@Body registro: RequestRegisterUser?): Response<User?>

    @GET("user")
    suspend fun getUsers(@Header("api-key") token: String?): Response<UserResponse>
}
