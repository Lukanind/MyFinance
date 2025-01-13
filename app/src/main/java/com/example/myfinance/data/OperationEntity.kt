package com.example.myfinance.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "operations"
)
data class OperationEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val amount: Double,
    val category: String,
    val description: String? = null
)