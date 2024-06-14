package com.example.recu_app.ui.view.fragments.users.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.databinding.ItemUserBinding
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.ui.view.activities.MainActivity

class UserAdapter(
    var userList: MutableList<User>,
    private val onDetailsClicked: (Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding, onDetailsClicked)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(
        private val binding: ItemUserBinding,
        private val onDetailsClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                tvName.text = user.nombre ?: ""
                tvEmail.text = user.email ?: ""

                btnEmail.setOnClickListener {
                    user.email?.let { email -> sendEmail(email) }
                }

                btnCall.setOnClickListener {
                    user.telefono?.let { phone -> initiateCall(phone) }
                }

                root.setOnClickListener {
                    user.id?.let { id -> onDetailsClicked(id) }
                }
            }
        }

        private fun sendEmail(email: String) {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            itemView.context.startActivity(emailIntent)
        }

        private fun initiateCall(phoneNumber: String) {
            val activity = itemView.context as? MainActivity
            activity?.makeCall(phoneNumber)
        }
    }
}