package com.example.recu_app.ui.viewmodel.users

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.R
import com.example.recu_app.domain.users.models.ListUsers
import com.example.recu_app.domain.users.models.Profile
import com.example.recu_app.domain.users.models.RepositoryUsers
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.domain.users.usecase.UseCaseLogin
import com.example.recu_app.domain.users.usecase.UseCaseRegisterLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel : ViewModel() {
    private val repositoryUsers = RepositoryUsers.repo
    private val useCaseLogin = UseCaseLogin(repositoryUsers)
    private val useCaseRegisterLogin = UseCaseRegisterLogin(repositoryUsers)

    val registrationStatusLiveData = MutableLiveData<Boolean>()
    val isLogginPreferencesLiveData = MutableLiveData<Boolean>(false)
    val userListLiveData = MutableLiveData<MutableList<User>>()
    val posNewUserLiveData = MutableLiveData<Int>()
    val posDeleteUserLiveData = MutableLiveData<Int>()

    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
        if (isUserLoggedInShared()) {
            Profile.profile.setUser(getUser())
            isLogginPreferencesLiveData.value = true
        }
    }

    fun isLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = if (email.isNotEmpty() && password.isNotEmpty()) {
                useCaseLogin.login(email, password)
            } else null

            withContext(Dispatchers.Main) {
                if (user != null) {
                    saveUserPreferences(user.id, user.name, user.email)
                    Profile.profile.setUser(getUser())
                    isLogginPreferencesLiveData.value = true
                } else {
                    isLogginPreferencesLiveData.value = false
                }
            }
        }
    }

    private fun isUserLoggedInShared(): Boolean {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_user_file), Context.MODE_PRIVATE)
        return sharedPreferences.contains(context.getString(R.string.pref_user_id))
    }

    private fun saveUserPreferences(id: Int, name: String, email: String) {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_user_file), Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(context.getString(R.string.pref_user_id), id)
            putString(context.getString(R.string.pref_user_name), name)
            putString(context.getString(R.string.pref_user_email), email)
            apply()
        }
    }

    private fun getUser(): User {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_user_file), Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt(context.getString(R.string.pref_user_id), 0)
        val name = sharedPreferences.getString(context.getString(R.string.pref_user_name), context.getString(R.string.default_user_name)) ?: ""
        val email = sharedPreferences.getString(context.getString(R.string.pref_user_email), context.getString(R.string.default_user_email)) ?: ""
        return User(id, name, email, "", "", "")
    }

    fun register(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered = useCaseRegisterLogin.register(user)
            withContext(Dispatchers.Main) {
                registrationStatusLiveData.value = isRegistered
            }
        }
    }

    // Other methods for managing user list, adding/deleting users, etc.
}
