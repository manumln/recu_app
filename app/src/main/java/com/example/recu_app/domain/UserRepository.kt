package com.example.recu_app.domain

import android.content.SharedPreferences
import com.example.recu_app.domain.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun login(username: String, password: String): User? {
        val savedUsername = sharedPreferences.getString(KEY_USERNAME, "")
        val savedPassword = sharedPreferences.getString(KEY_PASSWORD, "")

        return if (savedUsername == username && savedPassword == password) {
            User(savedUsername!!, "", savedPassword!!)
        } else {
            null
        }
    }

    fun registerUser(user: User) {
        sharedPreferences.edit().apply {
            putString(KEY_USERNAME, user.username)
            putString(KEY_EMAIL, user.email)
            putString(KEY_PASSWORD, user.password)
            apply()
        }
    }

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }
}
