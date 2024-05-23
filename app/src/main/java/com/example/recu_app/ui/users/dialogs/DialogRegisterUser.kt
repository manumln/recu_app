package com.example.recu_app.ui.users.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.recu_app.R
import com.example.recu_app.databinding.FragmentRegisterDialogBinding
import com.example.recu_app.domain.users.models.User

class DialogRegisterUser(
    val onNewUserDialog: (User) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(activity)

            val inflater = requireActivity().layoutInflater

            val viewDialogNewUser = inflater.inflate(R.layout.fragment_register_dialog, null)
            val binding = FragmentRegisterDialogBinding.bind(viewDialogNewUser)

            builder.setView(viewDialogNewUser)
                .setPositiveButton("Registrar Usuario",
                    DialogInterface.OnClickListener { dialog, id ->
                        val newUser = recoverDataLayout(binding)
                        if (
                            newUser.name.isNullOrEmpty() ||
                            newUser.email.isNullOrEmpty() ||
                            newUser.passw.isNullOrEmpty()
                        ) {
                            Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
                            dialog.cancel()
                        } else {
                            onNewUserDialog(newUser)
                        }
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun recoverDataLayout(binding: FragmentRegisterDialogBinding): User {
        return User(
            0,
            binding.nameEditText.text.toString(),
            binding.usernameEditText.text.toString(),
            binding.emailEditText.text.toString(),
            binding.phoneEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
    }
}
