package com.example.recu_app.domain.users.models

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.recu_app.data.users.database.UserDatabase
import com.example.recu_app.data.users.database.dao.UserDao

class UserDetailsRepository(application: Application) {

    private var userDao: UserDao? = null
    private var allData: LiveData<List<UserEntity>>? = null

    init {
        val db = UserDatabase.getDatabase(application)
        userDao = db?.daoAccess()
        allData = userDao?.getDetails()

    }

    fun getAllData(): LiveData<List<UserEntity>>? {
        return allData
    }

    fun insertData(data: UserEntity) {
        userDao?.let { LoginInsertion(it).execute(data) }
    }

    fun getUserById(userId: Int): LiveData<UserEntity>? {
        return userDao?.getUserById(userId)
    }

    private class LoginInsertion(private val userDao: UserDao) :
        AsyncTask<UserEntity, Void, Void>() {
        override fun doInBackground(vararg data: UserEntity): Void? {
            userDao.insertUserData(data[0])
            return null
        }
    }
}
