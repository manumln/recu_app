package com.example.recu_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recu_app.R
import com.example.recu_app.domain.models.User
import com.example.recu_app.ui.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var usernameText: TextInputEditText
    private lateinit var passwordText: TextInputEditText
    private lateinit var loginbtn: Button
    private lateinit var registerbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameText = findViewById(R.id.usernameEditText)
        passwordText = findViewById(R.id.passwordEditText)
        loginbtn = findViewById(R.id.loginButton)
        registerbtn = findViewById(R.id.registerFragmentButton)

        loginbtn.setOnClickListener {
            val enteredUsername = usernameText.text.toString()
            val enteredPassword = passwordText.text.toString()

            val loggedIn = loginViewModel.login(enteredUsername, enteredPassword)
            if (loggedIn) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas o usuario no registrado", Toast.LENGTH_SHORT).show()
            }
        }
        registerbtn.setOnClickListener {
            showRegisterDialog()
        }
    }

    private fun showRegisterDialog() {
        val dialog = RegisterDialogFragment()
        dialog.setRegisterCallback { name, user, password, email ->
            val newUser = User(user, email, password)
            loginViewModel.registerUser(newUser)
            Toast.makeText(this@LoginActivity, "Usuario registrado: $name", Toast.LENGTH_SHORT).show()
        }
        dialog.show(supportFragmentManager, "RegisterDialog")
    }
}
