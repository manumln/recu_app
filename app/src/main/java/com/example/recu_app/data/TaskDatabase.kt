package com.example.recu_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recu_app.data.Task
import com.example.recu_app.data.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

}