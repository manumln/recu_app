package com.example.recu_app.login.modelorentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class User : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var name: String? = null

    var mobileno: String? = null

    var dob: String? = null

    var password: String? = null


}