package com.example.recu_app.ui.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.R
import com.example.recu_app.data.users.database.UserDatabase
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.ui.viewmodel.users.LoginViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var etFullname: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etFullname = findViewById(R.id.et_fullname)
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etEmail = findViewById(R.id.et_email)
        etPhone = findViewById(R.id.et_phone)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        val userDetailsRepository = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            if (validation()) {
                userDetailsRepository.getGetAllData().observe(this, Observer { userList ->
                    var isUserExist = false

                    for (user in userList) {
                        if (user.username == etUsername.text.toString()) {
                            isUserExist = true
                            break
                        }
                    }

                    if (isUserExist) {
                        Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_LONG).show()
                    } else {
                        val user = User().apply {
                            name = etFullname.text.toString()
                            username = etUsername.text.toString()
                            password = etPassword.text.toString()
                            email = etEmail.text.toString()
                            phone = etPhone.text.toString()
                        }

                        UserDatabase.getDatabase(this)?.daoAccess()?.insertUserData(user)
                        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    private fun validation(): Boolean {
        if (etFullname.text.isNullOrEmpty()) {
            Toast.makeText(this, "Introduce nombre", Toast.LENGTH_LONG).show()
            return false
        }

        if (etUsername.text.isNullOrEmpty()) {
            Toast.makeText(this, "Introduce usuario", Toast.LENGTH_LONG).show()
            return false
        }

        if (etPassword.text.isNullOrEmpty()) {
            Toast.makeText(this, "Introduce contraseña", Toast.LENGTH_LONG).show()
            return false
        }

        if (etEmail.text.isNullOrEmpty()) {
            Toast.makeText(this, "Introduce email", Toast.LENGTH_LONG).show()
            return false
        }
        if (etPhone.text.isNullOrEmpty()) {
            Toast.makeText(this, "Introduce telefono", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
