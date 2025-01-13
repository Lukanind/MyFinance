package com.example.myfinance.data

data class OperationWithCategory(
    val operationId: Int,
    val amount: Double,
    val categoryName: String,
    val description: String?
)