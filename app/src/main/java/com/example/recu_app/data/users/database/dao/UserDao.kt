package com.example.recu_app.data.users.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recu_app.data.users.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM tblusers WHERE email = :email and password =:password")
    suspend fun login(email: String, password: String): UserEntity



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg user : UserEntity)

    @Query("SELECT * FROM tblusers")
    suspend fun getAllUsers(): List<UserEntity>
}