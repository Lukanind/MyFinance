package com.example.myfinance.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "operations",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OperationEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val amount: Double,
    val categoryId: Int,
    val description: String? = null
)