package com.example.recu_app.domain.users.models

class ListUsers private constructor(

){
    var users: MutableList<User> = mutableListOf()
    companion object{
        val list: ListUsers by lazy{
            ListUsers()
        }
    }
}