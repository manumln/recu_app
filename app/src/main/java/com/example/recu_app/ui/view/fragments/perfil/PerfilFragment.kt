package com.example.recu_app.ui.view.fragments.perfil

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.R
import com.example.recu_app.domain.users.models.UserEntity
import com.example.recu_app.ui.viewmodel.perfil.PerfilViewModel

class PerfilFragment : Fragment() {

    private lateinit var perfilViewModel: PerfilViewModel
    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        tvName = view.findViewById(R.id.tv_name)
        tvUsername = view.findViewById(R.id.tv_username)
        tvEmail = view.findViewById(R.id.tv_email)
        tvPhone = view.findViewById(R.id.tv_phone)

        val sharedPreferences = requireActivity().getSharedPreferences("AlertsPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1)

        perfilViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)
        perfilViewModel.init(userId)
        perfilViewModel.getUserById()?.observe(viewLifecycleOwner, Observer { userEntity ->
            userEntity?.let {
                displayUserData(userEntity)
            }
        })

        return view
    }

    private fun displayUserData(userEntity: UserEntity) {
        tvName.text = userEntity.name
        tvUsername.text = userEntity.username
        tvEmail.text = userEntity.email
        tvPhone.text = userEntity.phone
    }
}
