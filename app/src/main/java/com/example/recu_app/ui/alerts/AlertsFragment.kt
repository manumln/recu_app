package com.example.recu_app.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recu_app.databinding.FragmentAlertsBinding
import com.example.recu_app.domain.alerts.models.Alert
import com.example.recu_app.ui.alerts.adapter.AdapterAlerts
import com.example.recu_app.ui.viewmodel.AlertsViewModel

class AlertsFragment : Fragment() {
    private var _binding: FragmentAlertsBinding? = null
    private val binding get() = _binding!!
    private val viewModelAlerts: AlertsViewModel by viewModels()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterAlerts: AdapterAlerts

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        binding.myRecyclerViewAlerts.layoutManager = layoutManager

        adapterAlerts = AdapterAlerts(
            mutableListOf(),
            { pos -> deleteAlertForDialog(pos) },
            { pos -> detailsAlert(pos) }
        )
        binding.myRecyclerViewAlerts.adapter = adapterAlerts

        binding.btnAdd.setOnClickListener {
            addAlert()
        }

        setObserverChangeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteAlertForDialog(pos: Int) {
        // Mostrar un diálogo de confirmación de eliminación.
        // Si se confirma, llamar a okOnDeleteAlert(pos)
    }

    private fun okOnDeleteAlert(pos: Int) {
        viewModelAlerts.deleteAlert(pos)
    }

    private fun detailsAlert(pos: Int) {
        val navController = findNavController()
        val action = AlertsFragmentDirections.actionAlertsFragmentToDetailsAlertFragment(num = pos)
        navController.navigate(action)
    }

    private fun addAlert() {
        // Mostrar un diálogo para agregar una nueva alerta.
        // Al confirmar, llamar a okOnNewAlert(newAlert)
    }

    private fun okOnNewAlert(newAlert: Alert) {
        viewModelAlerts.addAlert(newAlert)
    }

    private fun setObserverChangeViewModel() {
        viewModelAlerts.listAlertsLiveData.observe(viewLifecycleOwner) { alerts ->
            adapterAlerts.listAlerts = alerts.toMutableList()
            adapterAlerts.notifyDataSetChanged()
        }

        viewModelAlerts.posNewAlertLiveData.observe(viewLifecycleOwner) { pos ->
            adapterAlerts.notifyItemInserted(pos)
            layoutManager.scrollToPosition(pos)
        }

        viewModelAlerts.posDeleteAlertLiveData.observe(viewLifecycleOwner) { pos ->
            adapterAlerts.notifyItemRemoved(pos)
        }
    }
}
