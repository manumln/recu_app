package com.example.recu_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.recu_app.databinding.ActivityLoginBinding
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.ui.viewmodel.users.UserViewModel
import com.example.recu_app.ui.users.dialogs.DialogRegisterUser

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerLiveData()
        initEvent()
        userViewModel.setContext(this)
    }

    private fun initEvent() {
        binding.loginButton.setOnClickListener { view ->
            userViewModel.isLogin(binding.usernameEditText.text.toString(), binding.passwordEditText.text.toString())
        }

        binding.registerFragmentButton.setOnClickListener {
            val dialog = DialogRegisterUser { user ->
                okOnRegisterUser(user)
            }
            dialog.show(this.supportFragmentManager, "Registro de nuevo usuario")
        }

    }

    private fun okOnRegisterUser(user: User) {
        userViewModel.register(user)
    }

    private fun registerLiveData() {
        userViewModel.isLogginPreferencesLiveData.observe(this, Observer { isLoggin ->
            if (isLoggin) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error en el logueo", Toast.LENGTH_LONG).show()
            }
        })

        userViewModel.registrationStatusLiveData.observe(this, Observer { register ->
            if (register) {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Usuario No registrado", Toast.LENGTH_LONG).show()
            }
        })
    }

}
