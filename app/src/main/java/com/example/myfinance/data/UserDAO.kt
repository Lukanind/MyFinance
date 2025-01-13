package com.example.myfinance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :userName AND password = :password LIMIT 1")
    suspend fun getUser(userName: String, password: String): UserEntity?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>
}