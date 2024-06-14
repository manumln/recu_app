package com.example.recu_app.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun saveUserData(token: String, email: String, name: String, phone: String) {
        prefs.edit().apply {
            putString("token", token)
            putString("email", email)
            putString("name", name)
            putString("phone", phone)
            apply()
        }
    }

    fun clearUserData() {
        prefs.edit().clear().apply()
    }
}
