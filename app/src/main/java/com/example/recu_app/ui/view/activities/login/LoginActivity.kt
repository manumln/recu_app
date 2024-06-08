package com.example.recu_app.ui.view.activities.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.R
import com.example.recu_app.ui.viewmodel.users.LoginViewModel
import com.example.recu_app.ui.view.activities.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)

        val userDetailsRepository = ViewModelProvider(this).get(LoginViewModel::class.java)

        sharedPreferences = getSharedPreferences("AlertsPrefs", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            if (validation()) {
                userDetailsRepository.getGetAllData().observe(this, Observer { userList ->
                    var isUserExist = false

                    for (user in userList) {
                        if (user.username == etUsername.text.toString()) {
                            if (user.password == etPassword.text.toString()) {
                                val userId = user.id 
                                val editor = sharedPreferences.edit()
                                editor.putBoolean("isLoggedIn", true)
                                editor.putInt("userId", userId)
                                editor.apply()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
                            }
                            isUserExist = true
                            break
                        }
                    }

                    if (!isUserExist) {
                        Toast.makeText(this@LoginActivity, "Usuario no registrado", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
    private fun validation(): Boolean {
        if (etUsername.text.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, "Introduce usuario", Toast.LENGTH_LONG).show()
            return false
        }
        if (etPassword.text.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, "Introduce contraseña", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
