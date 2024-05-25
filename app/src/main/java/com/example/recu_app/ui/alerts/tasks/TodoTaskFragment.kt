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
import com.example.recu_app.ui.alerts.tasks.TaskAdapter
import com.example.recu_app.data.SortOrder
import com.example.recu_app.data.Task
import com.example.recu_app.databinding.FragmentTodoTaskBinding
import com.example.recu_app.ui.alerts.tasks.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoTaskFragment : Fragment(), TaskAdapter.OnItemClickListener, MenuProvider {

    private var _binding: FragmentTodoTaskBinding? = null
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
        _binding = FragmentTodoTaskBinding.inflate(inflater, container, false)
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

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
            binding.emptyView.isVisible = it.isEmpty()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sort_by_due_date -> {
                viewModel.onSortOrderSelected(SortOrder.BY_DUE_DATE)
                true
            }

            R.id.action_sort_by_date_created -> {
                viewModel.onSortOrderSelected(SortOrder.BY_CREATED_DATE)
                true
            }

            else -> false
        }
    }

    override fun onItemClick(task: Task) {
        val action = TasksFragmentDirections.actionTaskFragmentToAddEditTaskFragment(task)
        findNavController().navigate(action)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(task, isChecked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}