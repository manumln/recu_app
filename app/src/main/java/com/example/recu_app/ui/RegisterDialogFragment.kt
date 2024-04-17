package com.example.recu_app.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.R
import com.example.recu_app.ui.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterDialogFragment : DialogFragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var registerCallback: ((name: String, user: String, password: String, email: String) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_register_dialog, null)

            val nameEditText = view.findViewById<TextInputEditText>(R.id.nameEditText)
            val usernameEditText = view.findViewById<TextInputEditText>(R.id.usernameEditText)
            val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)
            val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)

            val registerButton = view.findViewById<Button>(R.id.registerButton)
            val cancelButton = view.findViewById<Button>(R.id.cancelButton)

            registerButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val user = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()
                val email = emailEditText.text.toString()

                registerCallback?.invoke(name, user, password, email)
                dismiss()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("")
    }

    fun setRegisterCallback(callback: (name: String, user: String, password: String, email: String) -> Unit) {
        registerCallback = callback
    }
}
