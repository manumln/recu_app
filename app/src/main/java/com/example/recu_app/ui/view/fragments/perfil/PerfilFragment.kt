package com.example.recu_app.ui.view.fragments.perfil

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recu_app.R
import com.example.recu_app.databinding.FragmentPerfilBinding
import com.example.recu_app.ui.viewmodel.perfil.PerfilViewModel

class PerfilFragment : Fragment() {

    private lateinit var viewModel: PerfilViewModel
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)
        Log.d("PerfilFragment", "ViewModel instanciado")

        viewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            Log.d("PerfilFragment", "Datos del usuario observados: $user")
            user?.let {
                binding.tvEmail.text = ("Email: ${it.email}")
                binding.tvName.text = ("Nombre: ${it.nombre}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}