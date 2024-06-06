package com.example.recu_app.domain.users.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class UserEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var username: String? = null
    var password: String? = null
    var email: String? = null
    var phone: String? = null
}
