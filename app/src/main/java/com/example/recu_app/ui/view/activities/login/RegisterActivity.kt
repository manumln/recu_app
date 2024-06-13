package com.example.recu_app.ui.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recu_app.R
import com.example.recu_app.domain.users.models.RequestRegisterUser
import com.example.recu_app.domain.users.models.UserRegistrationListener

class RegisterActivity : AppCompatActivity() {

    private lateinit var usuario: EditText
    private lateinit var pass: EditText
    private lateinit var confirmPass: EditText
    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usuario = findViewById(R.id.et_username)
        pass = findViewById(R.id.et_password)
        confirmPass = findViewById(R.id.et_confirm_password)
        email = findViewById(R.id.et_email)

        val registerButton: Button = findViewById(R.id.btn_register)
        registerButton.setOnClickListener {
            val username = usuario.text.toString()
            val password = pass.text.toString()
            val confirmPassword = confirmPass.text.toString()
            val userEmail = email.text.toString()

            if (password == confirmPassword) {
                val registro = RequestRegisterUser().apply {
                    nombre = username
                    this.password = password
                }
                val mListener = this as? UserRegistrationListener
                mListener?.insertarUsuario(registro)
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        val loginButton: Button = findViewById(R.id.btn_login)
        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
