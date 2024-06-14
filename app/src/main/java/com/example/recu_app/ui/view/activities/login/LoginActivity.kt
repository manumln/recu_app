package com.example.recu_app.ui.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.recu_app.R
import com.example.recu_app.data.api.InstanceRetrofit
import com.example.recu_app.data.users.database.network.UserInterfaceApi
import com.example.recu_app.domain.users.models.Request.RequestLoginUser
import com.example.recu_app.ui.view.activities.MainActivity
import com.example.recu_app.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var usuario: EditText
    private lateinit var contraseña: EditText
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var userAPI: UserInterfaceApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuario = findViewById(R.id.et_username)
        contraseña = findViewById(R.id.et_password)
        sharedPreferencesManager = SharedPreferencesManager(applicationContext)
        userAPI = InstanceRetrofit.getUsers()

        if (sharedPreferencesManager.getToken() != null) {
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
        sharedPreferencesManager.saveUserData(token, email, name, phone)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
