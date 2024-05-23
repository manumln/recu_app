package com.example.recu_app.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.recu_app.R

class RegisterDialogFragment : DialogFragment() {

    private var registerCallback: ((String, String, String, String, String) -> Unit)? = null

    fun setRegisterCallback(callback: (String, String, String, String, String) -> Unit) {
        registerCallback = callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_register_dialog, null)

        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val usernameEditText = view.findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val phoneEditText = view.findViewById<EditText>(R.id.phoneEditText)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()

            registerCallback?.invoke(name, username, password, email, phone)
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}
