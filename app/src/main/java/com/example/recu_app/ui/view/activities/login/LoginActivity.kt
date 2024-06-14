package com.example.recu_app.ui.view.activities.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.recu_app.ui.view.activities.MainActivity
import com.example.recu_app.R
import com.example.recu_app.data.api.InstanceRetrofit
import com.example.recu_app.data.users.database.network.UserInterfaceApi
import com.example.recu_app.domain.users.models.Request.RequestLoginUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var usuario: EditText
    private lateinit var contraseña: EditText
    private lateinit var prefs: SharedPreferences
    private lateinit var userAPI: UserInterfaceApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuario = findViewById(R.id.et_username)
        contraseña = findViewById(R.id.et_password)
        prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userAPI = InstanceRetrofit.getUsers()

        if (getToken() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun handleLogin(view: View?) {
        val email = usuario.text.toString()
        val password = contraseña.text.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            val loginRequest = RequestLoginUser().apply {
                this.email = email
                this.password = password
            }

            try {
                val response = userAPI.login(loginRequest)
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        saveUserAndProceed(user.token ?: "", user.email ?: "", user.nombre ?: "", user.telefono ?: "")
                    } else {
                        runOnUiThread {
                            showError("Invalid response from server")
                        }
                    }
                } else {
                    runOnUiThread {
                        showError("Error logging in: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    showError("Network error: ${e.message}")
                }
            }
        }
    }

    fun handleRegistration(view: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun saveUserAndProceed(token: String, email: String, name: String, phone: String) {
        prefs.edit().apply {
            putString("token", token)
            putString("email", email)
            putString("name", name)
            putString("phone", phone)
            apply()
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun saveUserDetails(token: String, email: String, name: String, phone: String?) {
        prefs.edit().apply {
            putString("token", token)
            putString("email", email)
            putString("name", name)
            putString("phone", phone)
            apply()
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveToken(token: String) {
        prefs.edit().apply {
            putString("token", token)
            apply()
        }
    }

    private fun getToken(): String? {
        return prefs.getString("token", null)
    }
}
