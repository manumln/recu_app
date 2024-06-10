package com.example.recu_app.ui.view.fragments.users.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.R
import com.example.recu_app.domain.users.models.UserEntity
import androidx.core.app.ActivityCompat

class UserAdapter(private val context: Context, private var userEntityList: List<UserEntity>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setUsers(userEntities: List<UserEntity>) {
        this.userEntityList = userEntities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userEntityList[position]
        holder.tvUsername.text = user.username
        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
        holder.tvPhone.text = user.phone

        holder.btnCall.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:${user.phone}")
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show()
                // Solicitar permisos aquí si es necesario
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_PHONE
                )
            }
        }

        holder.btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${user.email}")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userEntityList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val btnCall: Button = itemView.findViewById(R.id.btnCall)
        val btnEmail: Button = itemView.findViewById(R.id.btnEmail)
    }

    companion object {
        private const val REQUEST_CALL_PHONE = 1
    }
}
