package com.example.recu_app.domain.users.models

import com.example.recu_app.data.users.database.UserDatabaseProvider
import com.example.recu_app.data.users.database.dao.UserDao
import com.example.recu_app.data.users.database.network.UserInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class UserRepository private constructor(
    private val userDao: UserDao
) {
    companion object {
        val repo: UserRepository by lazy {
            UserRepository(UserDatabaseProvider.userDao)
        }
    }

    private var loggedUser: User? = null

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2/api-pueblos/endp/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val userInterface: UserInterface = retrofit.create(UserInterface::class.java)

    fun setLoggedUser(user: User) {
        loggedUser = user
    }

    fun getLoggedUser(): User? {
        return loggedUser
    }

  /*  suspend fun getUsersApi(token: String): List<User> {
        return withContext(Dispatchers.IO) {
            val call = userInterface.getUsers(token)
            val response = call?.execute()
            if (response?.isSuccessful == true) {
                response.body()?.let {
                    Log.d("Repository", "Respuesta de la API: ${it.message}")
                    return@withContext it.message
                }
            } else {
                Log.e("Repository", "Error en la respuesta de la API: ${response?.errorBody()?.string()}")
            }
            return@withContext emptyList()
        }
    }*/

}
