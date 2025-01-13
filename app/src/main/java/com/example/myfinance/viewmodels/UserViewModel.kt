package com.example.myfinance.viewmodels

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

    fun registerUser(userName: String, password: String, role: String) = viewModelScope.launch {
        val user = UserEntity(userName = userName, password = password, role = role)
        database.userDao.addUser(user)
    }

    fun loginUser(username: String, password: String, onResult: (UserEntity?) -> Unit) = viewModelScope.launch {
        val user = database.userDao.getUser(username, password)
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
