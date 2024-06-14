package com.example.recu_app.ui.view.fragments.users

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recu_app.databinding.FragmentUsersBinding
import com.example.recu_app.ui.view.fragments.users.adapter.UserAdapter
import com.example.recu_app.ui.viewmodel.users.UserViewModel

class UsuariosFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private lateinit var prefs: SharedPreferences
    private lateinit var adapterUser: UserAdapter
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        token = prefs.getString("token", null) ?: ""

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(context)
        adapterUser = UserAdapter(mutableListOf()) { position ->
            // Implementa aquí la acción al hacer clic en un usuario si es necesario
        }
        binding.recyclerViewUsers.adapter = adapterUser

        userViewModel.usersLiveData.observe(viewLifecycleOwner, Observer { result ->
            result.fold(
                onSuccess = { users ->
                    adapterUser.userList = users.toMutableList()
                    adapterUser.notifyDataSetChanged()
                },
                onFailure = { error ->
                }
            )
        })

        if (token.isNotEmpty()) {
            userViewModel.showUsers(token)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
