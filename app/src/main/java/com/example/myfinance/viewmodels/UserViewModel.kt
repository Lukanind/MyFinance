package com.example.myfinance.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myfinance.App
import com.example.myfinance.data.MyDatabase
import com.example.myfinance.data.UserEntity
import kotlinx.coroutines.launch

class UserViewModel(val database: MyDatabase) : ViewModel() {

    fun registerUser(
        userName: String,
        password: String,
        role: String = "user",
        email: String,
        onResult: (Boolean?) -> Unit
    ) = viewModelScope.launch {
        val isUser = database.userDao.getUserByUserName(userName)
        if (isUser == null){
            val user = UserEntity(
                userName = userName,
                password = password,
                role = role,
                email = email
            )
            database.userDao.addUser(user)
            onResult(true)
            Log.d("registerUser", "onReasult: true")
        } else{
            onResult(false)
            Log.d("registerUser", "onReasult: true")
        }
    }

    fun loginUser(
        email: String,
        password: String,
        onResult: (UserEntity?) -> Unit
    ) = viewModelScope.launch {
        val user = database.userDao.getUser(email, password)
        onResult(user) // Возвращаем результат в главный поток
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return UserViewModel(database) as T
            }
        }
    }
}
