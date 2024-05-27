package com.example.recu_app.ui.view.fragments.users

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.R
import com.example.recu_app.ui.view.fragments.users.adapter.UserAdapter
import com.example.recu_app.ui.viewmodel.users.UserViewModel

class UsuariosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel
    private val REQUEST_CALL_PHONE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewUsers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        checkCallPermission()

        userAdapter = UserAdapter(requireContext(), emptyList())
        recyclerView.adapter = userAdapter

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getAllUsers().observe(viewLifecycleOwner, Observer { userList ->
            userAdapter.setUsers(userList)
        })

        return view
    }

    private fun checkCallPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PHONE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(context, "Permiso de llamada aceptado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permiso de llamada rechazado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
