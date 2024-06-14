package com.example.recu_app.ui.view.fragments.alerts

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recu_app.R
import com.example.recu_app.ui.view.fragments.alerts.adapter.AlertsAdapter
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.utils.converters.CoroutineUtils.executeInCoroutine
import com.example.recu_app.utils.converters.Resource
import com.example.recu_app.utils.converters.SnackBarUtils.showSnackBar
import com.example.recu_app.databinding.FragmentAllAlertsBinding
import com.example.recu_app.ui.viewmodel.alerts.AlertsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllAlertsFragment : Fragment(R.layout.fragment_all_alerts) {

    private var _binding: FragmentAllAlertsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AlertsViewModel>()
    private lateinit var adapter: AlertsAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 60000L // 1 minuto

    private val updateAlertsTask = object : Runnable {
        override fun run() {
            executeInCoroutine {
                viewModel.getAlerts()
            }
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllAlertsBinding.bind(view)
        setUpRecyclerView()

        viewModel.alerts.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (data.data?.isEmpty() == true) {
                        binding.tvNoAlerts.visibility = View.VISIBLE
                        adapter.updateAlerts(emptyList())
                    } else {
                        binding.tvNoAlerts.visibility = View.GONE
                        data.data?.let {
                            adapter.updateAlerts(it)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    requireContext().showSnackBar(
                        rootView = binding.root,
                        message = data.message.toString(),
                        anchorView = binding.btnAddAlert,
                    )
                }
                else -> {
                }
            }
        }

        binding.btnAddAlert.setOnClickListener {
            findNavController().navigate(R.id.action_allAlertsFragment_to_addAlertFragment)
        }

        handler.post(updateAlertsTask)
    }

    private fun setUpRecyclerView() {
        adapter = AlertsAdapter(emptyList()) { alertItemBinding, item ->
            alertItemBinding.ivDelete.setOnClickListener {
                viewModel.deleteAlert(item)
                requireContext().showSnackBar(
                    rootView = binding.root,
                    message = "Borrada",
                    anchorView = binding.btnAddAlert,
                    actionText = "Undo",
                    onAction = { undoDelete(item) }
                )
            }
        }
        binding.rvAlerts.layoutManager = LinearLayoutManager(activity)
        binding.rvAlerts.adapter = adapter
    }

    private fun hideProgressBar() {
    }

    private fun undoDelete(alert: AlertEntity) {
        executeInCoroutine {
            viewModel.createOrUpdateAlert(alert)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateAlertsTask)
        _binding = null
    }
}
