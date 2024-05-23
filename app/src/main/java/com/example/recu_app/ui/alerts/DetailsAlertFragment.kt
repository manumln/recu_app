package com.example.recu_app.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.recu_app.databinding.FragmentDetailsAlertBinding
import com.example.recu_app.ui.viewmodel.DetailsAlertViewModel

class DetailsAlertFragment : Fragment() {

    private var _binding: FragmentDetailsAlertBinding? = null
    private val binding get() = _binding!!
    private val myArgument: DetailsAlertFragmentArgs by navArgs()
    private val viewModelAlertDetails: DetailsAlertViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posAlertDetail = myArgument.num

        viewModelAlertDetails.alertLiveData.observe(viewLifecycleOwner) { alert ->
            binding.textViewAlertShortDescription.text = alert.textShort
            binding.textViewAlertShortDescription.text = alert.message
            binding.textViewAlertDate.text = alert.alertDate.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
