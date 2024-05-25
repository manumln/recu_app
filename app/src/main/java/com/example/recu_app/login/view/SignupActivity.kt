package com.example.recu_app.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.R
import com.example.recu_app.login.db.UserDatabase
import com.example.recu_app.login.modelorentity.User
import com.manumln.alertas.login.viewmodel.LoginViewModel

class SignupActivity : AppCompatActivity() {

    var isExist = false

    private lateinit var etFullname: EditText
    private lateinit var etMobileNo: EditText
    private lateinit var etPassword: EditText
    private lateinit var etDob: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Inicializar los elementos del layout
        etFullname = findViewById(R.id.et_fullname)
        etMobileNo = findViewById(R.id.et_mobile_no)
        etPassword = findViewById(R.id.et_password)
        etDob = findViewById(R.id.et_dob)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        // Llamar al ViewModel
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
                        if (user.mobileno == etMobileNo.text.toString()) {
                            isUserExist = true
                            break
                        }
                    }

                    if (isUserExist) {
                        Toast.makeText(this, "User Already Registered !!!", Toast.LENGTH_LONG).show()
                    } else {
                        val user = User().apply {
                            dob = etDob.text.toString()
                            name = etFullname.text.toString()
                            mobileno = etMobileNo.text.toString()
                            password = etPassword.text.toString()
                        }

                        UserDatabase.getDatabase(this)?.daoAccess()?.insertUserData(user)
                        Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    private fun validation(): Boolean {
        if (etFullname.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Full Name", Toast.LENGTH_LONG).show()
            return false
        }

        if (etMobileNo.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_LONG).show()
            return false
        }

        if (etMobileNo.text.toString().length != 10) {
            Toast.makeText(this, "Enter 10 digit Mobile Number", Toast.LENGTH_LONG).show()
            return false
        }

        if (etPassword.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}
