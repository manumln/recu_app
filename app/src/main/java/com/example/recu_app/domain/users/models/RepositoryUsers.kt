package com.example.recu_app.domain.users.models

import com.example.recu_app.data.users.database.dao.UserDao
import com.example.recu_app.data.users.database.entities.UserEntity
import com.example.recu_app.domain.UserDataBaseSingleton

class RepositoryUsers  private constructor(private val userDao : UserDao){
    companion object{
        val repo: RepositoryUsers by lazy {
            RepositoryUsers(UserDataBaseSingleton.userDao)  //le pasamos el singleton. Aunque no será necesario.
        }
    }

    suspend fun isLoginEntity(email: String, passord: String): User?{
        val posUser : UserEntity = userDao.login(email, passord)
        var user : User ? = null
        if (posUser != null)
            user= User(posUser.id, posUser.name, posUser.password, posUser.email, posUser.phone, posUser.imag )
        return user
    }



    suspend fun registerEntity(user: User): Boolean{

        val exitUser = isLoginEntity(user.email, user.passw)
        if (exitUser == null){
            val userEntity = UserEntity(user.id, user.name, user.email, user.passw, user.phone, user.imagen)
            userDao.insertUser(userEntity)
            return true
        }else
            return false

    }
}