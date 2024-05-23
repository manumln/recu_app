package com.example.recu_app.ui.alerts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.recu_app.databinding.FragmentAlertsBinding
import com.example.recu_app.ui.viewmodel.DetailsAlertViewModel

class DetailsAlertFragment : Fragment() {

    private var _binding: FragmentAlertsBinding? = null
    private val binding get() = _binding!!
    private val myArgument: DetailsAlertFragmentArgs by navArgs()
    private val viewModelAlertDetails: DetailsAlertViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posAlertDetail = myArgument.num // Argument passed from the other fragment

        // Observe changes in the ViewModel and update the UI accordingly
        viewModelAlertDetails.alertLiveData.observe(viewLifecycleOwner) { alert ->
            // TODO: Display the alert in the UI
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
