package com.example.recu_app.data.users.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recu_app.domain.users.models.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insertUserData(userEntity: UserEntity)

    @Query("select * from UserEntity")
    fun getDetails(): LiveData<List<UserEntity>>

    @Query("DELETE FROM UserEntity WHERE id = :id")
    fun deleteByUserId(id: Long)
}