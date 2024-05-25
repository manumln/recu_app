package com.example.recu_app.data

import com.example.recu_app.data.SortOrder
import com.example.recu_app.data.Task
import com.example.recu_app.data.TaskDao
import com.example.recu_app.data.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {

    override fun getTasks(
        sortOrder: SortOrder,
        expired: Boolean
    ): Flow<List<Task>> = taskDao.getTasks(sortOrder, expired)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override suspend fun deleteExpiredTasks() = taskDao.deleteExpiredTasks()

    override suspend fun insertTask(task: Task) = taskDao.insert(task)
}