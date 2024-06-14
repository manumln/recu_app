package com.example.recu_app.ui.view.fragments.alerts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recu_app.R
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.utils.converters.CoroutineUtils.executeInCoroutine
import com.example.recu_app.utils.converters.SnackBarUtils.showSnackBar
import com.example.recu_app.databinding.FragmentAddAlertBinding
import com.example.recu_app.ui.viewmodel.alerts.AlertsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAlertFragment : Fragment(R.layout.fragment_add_alert) {

    private var _binding: FragmentAddAlertBinding? = null
    private val binding get() = _binding!!
    private val args: AddAlertFragmentArgs by navArgs()
    private var key: Int? = null
    private val viewModel by viewModels<AlertsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddAlertBinding.bind(view)

        if (args.alert != null) {
            val alert = args.alert
            binding.etAlerttitle.setText(alert?.title)
            binding.description.setText(alert?.description)
            binding.deadline.text = alert?.deadLine
            key = alert?.id
        }

        binding.date.setOnClickListener {
            showDatePicker()
        }

        binding.myToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSaveAlert.setOnClickListener {
            val title = binding.etAlerttitle.text.toString()
            val description = binding.description.text.toString()
            val deadLine = binding.deadline.text.toString()
            val isInputValid = validateInput(title, description, deadLine)

            if (isInputValid.first) {
                val anAlert = AlertEntity(
                    id = key,
                    title = title,
                    description = description,
                    deadLine = deadLine
                )
                saveOrUpdateAlert(anAlert)
                requireContext().showSnackBar(binding.root, "Alerta creada")
                findNavController().navigate(R.id.action_addAlertFragment_to_allAlertsFragment)
            } else {
                requireContext().showSnackBar(binding.root, isInputValid.second)
            }
        }
    }

    private fun showDatePicker() {
        val datePickerFragment = DatePickerFragment { deadLine ->
            binding.deadline.text = deadLine
            binding.date.text = "Cambiar fecha"
        }
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    private fun validateInput(
        title: String,
        description: String,
        deadline: String,
    ): Pair<Boolean, String> {
        var message = ""
        if (title.isEmpty()) {
            message = "Introduce un mensaje"
            return Pair(false, message)
        }
        if (description.isEmpty()) {
            message = "Introduce una descripción"
            return Pair(false, message)
        }
        if (deadline.isEmpty()) {
            message = "Introduce una fecha"
            return Pair(false, message)
        }
        return Pair(true, message)
    }

    private fun saveOrUpdateAlert(alert: AlertEntity) {
        executeInCoroutine {
            if (alert.id == null) {
                viewModel.createOrUpdateAlert(alert)
            } else {
                viewModel.createOrUpdateAlert(alert)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
