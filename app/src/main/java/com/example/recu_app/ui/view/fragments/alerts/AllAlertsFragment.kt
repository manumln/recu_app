package com.example.recu_app.ui.view.fragments.alerts

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recu_app.R
import com.example.recu_app.ui.view.fragments.alerts.adapter.AlertsAdapter
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.data.users.database.converters.CoroutineUtils.executeInCoroutine
import com.example.recu_app.data.users.database.converters.Resource
import com.example.recu_app.data.users.database.converters.SnackBarUtils.showSnackBar
import com.example.recu_app.databinding.FragmentAllAlertsBinding
import com.example.recu_app.ui.viewmodel.alerts.AddAlertsViewModel
import com.example.recu_app.ui.viewmodel.alerts.AllAlertsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllAlertsFragment : Fragment(R.layout.fragment_all_alerts) {

    private var _binding: FragmentAllAlertsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AllAlertsViewModel>()
    private val addAlertsViewModel by viewModels<AddAlertsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllAlertsBinding.bind(view)
        setUpRecyclerView()

        executeInCoroutine {
            viewModel.getAlerts()

            viewModel.alerts.observe(viewLifecycleOwner) { data ->
                when (data) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (data.data?.isEmpty() == true) {
                            binding.tvNoAlerts.visibility = VISIBLE
                            binding.rvAlerts.adapter = AlertsAdapter(emptyList())
                        } else {
                            binding.tvNoAlerts.visibility = GONE
                            binding.rvAlerts.layoutManager = LinearLayoutManager(context)
                            binding.rvAlerts.adapter = data.data?.let {
                                AlertsAdapter(it) { alertItemBinding, item ->
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
        }

        binding.btnAddAlert.setOnClickListener {
            findNavController().navigate(R.id.action_allAlertsFragment_to_addAlertFragment)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvAlerts.layoutManager = LinearLayoutManager(activity)
    }

    private fun hideProgressBar() {
    }

    private fun undoDelete(alert: AlertEntity) {
        executeInCoroutine {
            addAlertsViewModel.saveAlert(alert)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
