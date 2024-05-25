package com.example.recu_app.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val expired: Boolean = false,
    val dueDate: Long = 0,
    val createdDate: Long = System.currentTimeMillis(),
    val expiredDate: Long = 0
) : Parcelable