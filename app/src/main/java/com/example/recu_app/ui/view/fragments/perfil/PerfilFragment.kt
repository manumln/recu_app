package com.example.recu_app.ui.view.fragments.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.databinding.FragmentPerfilBinding
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.ui.viewmodel.perfil.PerfilViewModel

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: PerfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeUserProfile()
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)
    }

    private fun observeUserProfile() {
        profileViewModel.userProfile.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                updateUIWithUserData(it)
            }
        })
    }

    private fun updateUIWithUserData(user: User) {
        binding.tvName.text = user.nombre ?: ""
        binding.tvEmail.text = user.email ?: ""
        binding.tvPhone.text = user.telefono ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
