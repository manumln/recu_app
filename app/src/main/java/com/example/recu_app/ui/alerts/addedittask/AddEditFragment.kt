package com.example.recu_app.ui.alerts.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recu_app.R
import com.example.recu_app.databinding.FragmentAddEditBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.recu_app.util.Util.formattedDate
import com.example.recu_app.util.Util.showSnackbarWithAnchor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DateFormat


@AndroidEntryPoint
class AddEditFragment : Fragment(), MenuProvider {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AddEditViewModel>()
    private val args: AddEditFragmentArgs by navArgs()

    private var selectedDueDate: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.task = args.task
        selectedDueDate = args.task?.dueDate ?: 0

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.task?.let { task ->
            binding.apply {
                etTask.setText(task.name)
                cbCompleted.isChecked = task.expired == true
                txtAddDate.isVisible = task.dueDate.toInt() == 0
                chipDate.isVisible = task.dueDate.toInt() != 0
                chipDate.text = task.dueDate.formattedDate()
            }
        }

        clickListener()
        observeEvents()
    }

    private fun clickListener() {
        binding.apply {
            lytAddDate.setOnClickListener {
                showDatePicker()
            }

            chipDate.setOnClickListener {
                showDatePicker(selectedDueDate)
            }

            chipDate.setOnCloseIconClickListener {
                selectedDueDate = 0
                binding.txtAddDate.isVisible = true
                binding.chipDate.isVisible = false
            }

            fabSave.setOnClickListener {
                if (binding.etTask.text.isNullOrBlank()) {
                    it.showSnackbarWithAnchor("Task cannot be empty")
                } else {
                    viewModel.onSaveClick(
                        title = binding.etTask.text.toString(),
                        isExpired = binding.cbCompleted.isChecked,
                        dueDate = selectedDueDate
                    )
                }
            }
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addEditTaskEvent.collect { event ->
                    when (event) {
                        is AddEditViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
                            setFragmentResult(
                                "add_edit_request",
                                bundleOf("add_edit_result" to event.result)
                            )
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        menu.findItem(R.id.action_delete).isVisible = viewModel.task != null
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_delete -> {
                deleteTaskDialog()
                true
            }
            else -> false
        }
    }

    private fun deleteTaskDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Delete this task?")
            .setMessage("This action cannot be undone")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteTask()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDatePicker(selectedDate: Long = 0) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select due date")
            .setSelection(if (selectedDate.toInt() == 0) MaterialDatePicker.todayInUtcMilliseconds() else selectedDate)
            .build()

        datePicker.addOnPositiveButtonClickListener {
            selectedDueDate = it
            binding.txtAddDate.isVisible = false
            binding.chipDate.isVisible = true
            binding.chipDate.text = DateFormat.getDateInstance().format(it)
        }

        datePicker.show(childFragmentManager, "datePicker")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}