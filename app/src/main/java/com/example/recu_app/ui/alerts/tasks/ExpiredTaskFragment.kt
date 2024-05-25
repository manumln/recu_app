package com.example.recu_app.ui.alerts.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recu_app.R
import com.example.recu_app.databinding.FragmentExpiredTaskBinding
import com.example.recu_app.data.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpiredTaskFragment : Fragment(), TaskAdapter.OnItemClickListener, MenuProvider {

    private var _binding: FragmentExpiredTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<TaskViewModel>()

    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpiredTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            recyclerView.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.completedTasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
            binding.emptyView.isVisible = it.isEmpty()
        }

    }

    override fun onItemClick(task: Task) {
        val action = TasksFragmentDirections.actionTaskFragmentToAddEditTaskFragment(task)
        findNavController().navigate(action)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(task, isChecked)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        menu.findItem(R.id.action_sort).isVisible = false
        menu.findItem(R.id.action_delete_all).isVisible = true
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_delete_all -> {
                deleteAllCompletedTask()
                true
            }

            else -> false
        }
    }

    private fun deleteAllCompletedTask() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Delete all completed task?")
            .setMessage("This action cannot be undone")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteAllCompletedTask()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}