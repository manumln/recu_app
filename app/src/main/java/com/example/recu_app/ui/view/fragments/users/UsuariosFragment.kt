package com.example.recu_app.ui.view.fragments.users

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recu_app.R
import com.example.recu_app.databinding.FragmentUsersBinding
import com.example.recu_app.utils.SharedPreferencesManager
import com.example.recu_app.ui.view.fragments.users.adapter.UserAdapter
import com.example.recu_app.ui.viewmodel.users.UserViewModel

class UsuariosFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private lateinit var adapterUser: UserAdapter
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

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

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(context)
        adapterUser = UserAdapter(mutableListOf()) { position ->

        }
        binding.recyclerViewUsers.adapter = adapterUser

        val token = sharedPreferencesManager.getToken()

        userViewModel.usersLiveData.observe(viewLifecycleOwner, Observer { result ->
            result.fold(
                onSuccess = { users ->
                    adapterUser.listUsers = users.toMutableList()
                    adapterUser.notifyDataSetChanged()
                },
                onFailure = { error ->
                }
            )
        })
        if (token != null) {
            userViewModel.showUsers(token)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                sharedPreferencesManager.clearUserData()
                requireActivity().finish()
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
