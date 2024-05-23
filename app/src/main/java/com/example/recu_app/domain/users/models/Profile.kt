package com.example.recu_app.domain.users.models

class Profile private constructor(){

    lateinit var user : User //Tengo aquí el usuario logueado
    //....Puedo poner más atributos del Perfil, información que sea importante.

    companion object{
        val profile: Profile by lazy {
            Profile()  //creo el objeto singleton
        }
    }


    fun setUser (_user : User){
        user = _user
    }
}