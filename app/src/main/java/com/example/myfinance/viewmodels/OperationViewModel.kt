package com.example.myfinance.viewmodels

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myfinance.App
import com.example.myfinance.data.MyDatabase
import com.example.myfinance.data.OperationEntity
import com.example.myfinance.data.OperationWithCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OperationViewModel(val database: MyDatabase) : ViewModel() {

    val itemsList = database.operationDao.getAllOperations()

    //val operationsWithCategories= database.operationDao.getOperationsWithCategories()

    val newAmount = mutableDoubleStateOf(0.0)
    val newCategory = mutableStateOf("")
    val newDescription = mutableStateOf("")
    var newEntity: OperationEntity? = null

    fun insertItem() = viewModelScope.launch {
        val newItem = newEntity?.copy(
            amount = newAmount.doubleValue,
            category = newCategory.value,
            description = newDescription.value)
            ?: OperationEntity(
                amount = newAmount.doubleValue,
                category = newCategory.value,
                description = newDescription.value)
        database.operationDao.addOperation(newItem)
        newEntity = null
        newAmount.doubleValue = 0.0
        newCategory.value = ""
        newDescription.value = ""
    }

    fun deleteItem(item: OperationEntity) = viewModelScope.launch {
        database.operationDao.deleteOperation(item)
    }

    fun getItemById(id: Int, onResult: (OperationEntity) -> Unit) = viewModelScope.launch {
        val item = withContext(Dispatchers.IO) {
            database.operationDao.getOperationById(id)
        }
        onResult(item)  // Возвращаем результат в главный поток
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return OperationViewModel(database) as T
            }
        }
    }
}