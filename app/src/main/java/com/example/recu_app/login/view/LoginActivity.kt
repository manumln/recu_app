package com.example.recu_app.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.MainActivity
import com.example.recu_app.R
import com.manumln.alertas.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    var isExist = false

    private lateinit var etMobileNo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar los elementos del layout
        etMobileNo = findViewById(R.id.et_mobile_no)
        etPassword = findViewById(R.id.et_password)
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)

        val userDetailsRepository = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            if (validation()) {
                userDetailsRepository.getGetAllData().observe(this, Observer { userList ->
                    var isUserExist = false

                    for (user in userList) {
                        if (user.mobileno == etMobileNo.text.toString()) {
                            if (user.password == etPassword.text.toString()) {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                    putExtra("UserDetails", user)
                                }
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@LoginActivity, "Password is Incorrect", Toast.LENGTH_LONG).show()
                            }
                            isUserExist = true
                            break
                        }
                    }

                    if (!isUserExist) {
                        Toast.makeText(this@LoginActivity, "User Not Registered", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    private fun validation(): Boolean {
        if (etMobileNo.text.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, "Enter Mobile Number", Toast.LENGTH_LONG).show()
            return false
        }
        if (etMobileNo.text.toString().length != 10) {
            Toast.makeText(this@LoginActivity, "Enter 10 digit Mobile Number", Toast.LENGTH_LONG).show()
            return false
        }
        if (etPassword.text.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, "Enter Password", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
