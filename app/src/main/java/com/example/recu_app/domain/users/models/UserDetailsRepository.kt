package com.example.recu_app.domain.users.models

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.recu_app.data.users.database.dao.UserDao
import com.example.recu_app.data.users.database.UserDatabase

class UserDetailsRepository(application: Application) {

    private var userDao: UserDao? = null
    private var allData: LiveData<List<User>>? = null

    init {
        val db = UserDatabase.getDatabase(application)
        userDao = db?.daoAccess()
        allData = userDao?.getDetails()

    }
    fun getAllData(): LiveData<List<User>>? {
        return allData
    }
    fun insertData(data: User) {
        userDao?.let { LoginInsertion(it).execute(data) }
    }

    private class LoginInsertion(private val userDao: UserDao) :
        AsyncTask<User, Void, Void>() {
        override fun doInBackground(vararg data: User): Void? {
            userDao.insertUserData(data[0])
            return null

        }

    }
}