package com.example.recu_app.data

import com.example.recu_app.data.SortOrder
import com.example.recu_app.data.Task
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer.
 */
interface TaskRepository {

    fun getTasks(sortOrder: SortOrder, completed: Boolean): Flow<List<Task>>

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun deleteExpiredTasks()

    suspend fun insertTask(task: Task)


}