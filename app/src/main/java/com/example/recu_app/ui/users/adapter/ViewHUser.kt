package com.example.recu_app.ui.users.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.databinding.ItemUserBinding
import com.example.recu_app.domain.users.models.User

class ViewHUser (view: View,
                 val onDetails: (Int) -> Unit

) : RecyclerView.ViewHolder(view){

    lateinit var binding: ItemUserBinding
    init {
        binding = ItemUserBinding.bind(view)
    }


    fun renderize(user: User, position:Int){
        /*
        Debemos de setear los campos
         */


        binding.btnDetail.setOnClickListener{
            onDetails(position)
        }

    }
}