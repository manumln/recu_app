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

    @Query("SELECT * FROM UserEntity")
    fun getDetails(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<UserEntity>
}
