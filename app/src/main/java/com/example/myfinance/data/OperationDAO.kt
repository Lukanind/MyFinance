package com.example.myfinance.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOperation(operation: OperationEntity)

    @Query("SELECT * FROM operations")
    fun getAllOperations(): Flow<List<OperationEntity>>

    @Query("SELECT * FROM operations WHERE id = :operationId")
    fun getOperationById(operationId: Int): OperationEntity

//    @Transaction
//    @Query("SELECT o.id as operationId, o.amount, o.description, c.categoryName FROM operations o INNER JOIN categories c ON o.categoryId = c.id")
//    fun getOperationsWithCategories(): Flow<List<OperationWithCategory>>

//    @Update
//    suspend fun updateOperation(operation: OperationEntity)

    @Delete
    suspend fun deleteOperation(operation: OperationEntity)
}