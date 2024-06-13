package com.example.recu_app.ui.view.fragments.users.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.databinding.ItemUserBinding
import com.example.recu_app.domain.users.models.User

class UserAdapter(
    var listUsers: MutableList<User>,
    val onDetails: (Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHUser>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHUser {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return ViewHUser(binding, onDetails)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHUser, position: Int) {
        holder.renderize(listUsers[position], position)
    }

    inner class ViewHUser(private val binding: ItemUserBinding, val onDetails: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun renderize(user: User, position: Int) {
            binding.tvName.text = user.nombre
            binding.tvEmail.text = user.email

            binding.btnEmail.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${user.email}")
                    putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                    putExtra(Intent.EXTRA_TEXT, "Body Here")
                }
                itemView.context.startActivity(emailIntent)
            }

            // Configurar el botón de llamada telefónica
            binding.btnCall.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${user.id}")
                }
                itemView.context.startActivity(callIntent)
            }
        }
    }
}
