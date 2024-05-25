package com.example.recu_app.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recu_app.R
import com.example.recu_app.login.modelorentity.User

class PerfilFragment : Fragment() {

    private val perfilViewModel: PerfilViewModel by activityViewModels()

    private lateinit var tvFullname: TextView
    private lateinit var tvMobileno: TextView
    private lateinit var tvDob: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        // Inicializar los elementos del layout
        tvFullname = view.findViewById(R.id.tv_fullname)
        tvMobileno = view.findViewById(R.id.tv_mobileno)
        tvDob = view.findViewById(R.id.tv_dob)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener los detalles del usuario desde el ViewModel
        perfilViewModel.userDetails.observe(viewLifecycleOwner, { userDetails ->
            userDetails?.let {
                tvFullname.text = it.name
                tvMobileno.text = it.mobileno
                tvDob.text = it.dob
            }
        })

        // Si los detalles del usuario están pasados como argumento del fragmento
        val userDetails = arguments?.getSerializable("UserDetails") as? User
        userDetails?.let {
            perfilViewModel.setUserDetails(it)
        }
    }
}
