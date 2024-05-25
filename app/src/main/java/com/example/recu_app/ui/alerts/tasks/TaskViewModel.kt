@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.recu_app.ui.alerts.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recu_app.data.PreferenceManager
import com.example.recu_app.data.SortOrder
import com.example.recu_app.data.Task
import com.example.recu_app.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val sortOrderFlow = preferenceManager.sortOrderFlow

    @OptIn(ExperimentalCoroutinesApi::class)
    private val taskFlow = sortOrderFlow.flatMapLatest { sortOrder ->
        taskRepository.getTasks(sortOrder, false)
    }
    val tasks = taskFlow.asLiveData()

    private val completedTaskFlow = taskRepository.getTasks(SortOrder.BY_EXPIRED_DATE, true)
    val completedTasks = completedTaskFlow.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferenceManager.updateSortOrder(sortOrder)
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskRepository.updateTask(task.copy(expired = isChecked))
    }

    fun deleteAllCompletedTask() = viewModelScope.launch {
        taskRepository.deleteExpiredTasks()
    }

}