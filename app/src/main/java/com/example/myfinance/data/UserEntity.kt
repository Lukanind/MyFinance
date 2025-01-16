package com.example.myfinance.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val userName: String,
    val password: String,
    val role: String,
    val email: String
)