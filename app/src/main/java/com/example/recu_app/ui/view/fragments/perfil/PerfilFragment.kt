package com.example.recu_app.ui.view.fragments.perfil

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.R
import com.example.recu_app.databinding.FragmentPerfilBinding
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.ui.viewmodel.perfil.PerfilViewModel

class PerfilFragment : Fragment() {

    private var bindingInstance: FragmentPerfilBinding? = null
    private val binding get() = bindingInstance!!
    private lateinit var perfilViewModel: PerfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeUserData()
    }

    private fun setupViewModel() {
        perfilViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)
    }

    private fun observeUserData() {
        perfilViewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                updateUserUI(it)
            }
        })
    }

    private fun updateUserUI(user: User) {
        binding.tvName.text = getString(R.string.email_placeholder, user.email)
        binding.tvEmail.text = getString(R.string.name_placeholder, user.nombre)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}
