package com.example.recu_app.data

import androidx.room.*
import com.example.recu_app.data.SortOrder
import com.example.recu_app.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    fun getTasks(sortOrder: SortOrder, expired: Boolean): Flow<List<Task>> {
        return when (sortOrder) {
            SortOrder.BY_DUE_DATE -> getTaskSortedByDueDate(expired)
            SortOrder.BY_CREATED_DATE -> getTaskSortedByCreatedDate(expired)
            SortOrder.BY_EXPIRED_DATE -> getTaskSortedByExpiredDate(expired)
        }
    }

    @Query("SELECT * FROM task_table WHERE expired == :expired ORDER BY createdDate DESC")
    fun getTaskSortedByCreatedDate(expired: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE expired == :expired ORDER BY CASE WHEN dueDate = 0 THEN createdDate ELSE dueDate END")
    fun getTaskSortedByDueDate(expired: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE expired == :expired ORDER BY expiredDate DESC")
    fun getTaskSortedByExpiredDate(expired: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table WHERE expired = 1")
    suspend fun deleteExpiredTasks()

}