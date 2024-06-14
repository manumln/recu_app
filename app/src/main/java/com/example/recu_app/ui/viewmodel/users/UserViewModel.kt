package com.example.recu_app.ui.viewmodel.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.data.api.InstanceRetrofit
import com.example.recu_app.data.users.database.UserResponse
import com.example.recu_app.domain.users.models.Request.RequestLoginUser
import com.example.recu_app.domain.users.models.Request.RequestRegisterUser
import com.example.recu_app.domain.users.models.User
import com.example.recu_app.domain.users.models.listener.UserRegistrationListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel : ViewModel(), UserRegistrationListener {
    private val _usersLiveData = MutableLiveData<Result<List<User>>>()
    val usersLiveData: LiveData<Result<List<User>>> = _usersLiveData

    private val userAPI = InstanceRetrofit.getUsers()

    fun showUsers(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getUsers(token)
                if (response.isSuccessful) {
                    val usersResponse = response.body()
                    val users = usersResponse?.usuarios ?: emptyList()
                    _usersLiveData.postValue(Result.success(users))
                } else {
                    _usersLiveData.postValue(Result.failure(Exception("Error fetching users: ${response.message()}")))
                }
            } catch (e: Exception) {
                _usersLiveData.postValue(Result.failure(e))
            }
        }
    }

    suspend fun login(login: RequestLoginUser?): Response<User?> {
        return userAPI.login(login)
    }

    override fun insertUser(registro: RequestRegisterUser?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = register(registro)
                if (response.isSuccessful) {
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }

    private suspend fun register(registro: RequestRegisterUser?): Response<User?> {
        return userAPI.registro(registro)
    }

    private suspend fun getUsers(token: String?): Response<UserResponse> {
        return userAPI.getUsers(token)
    }
}
