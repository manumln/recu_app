package com.example.recu_app.ui.alerts.tasks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recu_app.R
import com.example.recu_app.databinding.FragmentTasksBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.example.recu_app.util.Constants
import com.example.recu_app.util.Util.showSnackbarWithAnchor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val tabTitleArray = arrayOf(
        "Alertas")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                TasksFragmentDirections.actionTaskFragmentToAddEditTaskFragment(null)
            )
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            Log.d("TAG", "onViewCreated: $result")
            when(result){
                Constants.ADD_TASK_RESULT_OK -> {
                    binding.fabAdd.showSnackbarWithAnchor("Task added")
                }
                Constants.EDIT_TASK_RESULT_OK -> {
                    binding.fabAdd.showSnackbarWithAnchor("Task updated")
                }
                Constants.DELETE_TASK_RESULT_OK -> {
                    binding.fabAdd.showSnackbarWithAnchor("Task deleted")
                }
            }
        }
    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return NUM_TABS
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return TodoTaskFragment()
                1 -> return ExpiredTaskFragment()
            }
            return TodoTaskFragment()
        }
    }


    companion object {
        private const val NUM_TABS = 2
    }
}