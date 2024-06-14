package com.example.recu_app.ui.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.recu_app.R
import com.example.recu_app.data.api.InstanceRetrofit
import com.example.recu_app.domain.users.models.Request.RequestRegisterUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var email: EditText
    private lateinit var telefono: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)
        confirmPassword = findViewById(R.id.et_confirm_password)
        email = findViewById(R.id.et_email)
        telefono = findViewById(R.id.et_telefono)

        // Register button click listener
        findViewById<Button>(R.id.btn_register).setOnClickListener {
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()
            val confirmPasswordText = confirmPassword.text.toString()
            val emailText = email.text.toString()

            if (passwordText == confirmPasswordText) {
                val registerUser = RequestRegisterUser().apply {
                    nombre = usernameText
                    this.password = passwordText
                    this.email = emailText
                }
                insertUser(registerUser)
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun insertUser(registerUser: RequestRegisterUser?) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = InstanceRetrofit.getUsers().registro(registerUser)
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                } else {
                    runOnUiThread {
                        val errorMessage = response.errorBody()?.string()
                        Toast.makeText(this@RegisterActivity, "Error en el registro del usuario: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
