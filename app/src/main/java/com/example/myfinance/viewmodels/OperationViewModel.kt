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

    val operationsWithCategories = database.operationDao.getOperationsWithCategories()

    val newAmount = mutableDoubleStateOf(0.0)
    //val newCategoryId = mutableStateOf(0)
    val newCategory = mutableStateOf("")
    val newDescription = mutableStateOf("")
    var newEntity: OperationEntity? = null

    fun insertItem(categoryViewModel: CategoryViewModel) = viewModelScope.launch {
        val newItem = newEntity?.copy(
            amount = newAmount.doubleValue,
            categoryId = categoryViewModel.currentId.intValue,
            description = newDescription.value)
            ?: OperationEntity(
                amount = newAmount.doubleValue,
                categoryId = categoryViewModel.currentId.intValue,
                description = newDescription.value)
        database.operationDao.addOperation(newItem)
        newEntity = null
        newAmount.doubleValue = 0.0
        newCategory.value = ""
        newDescription.value = ""
        //newCategoryId.value = 0
    }

    fun deleteItem(item: OperationEntity) = viewModelScope.launch {
        database.operationDao.deleteOperation(item)
    }

    fun deleteItem(item: OperationWithCategory) = viewModelScope.launch {
        database.operationDao.deleteOperation(
            database.operationDao.getOperationById(item.operationId)
        )
    }

    fun getItemById(id: Int, onResult: (OperationEntity) -> Unit) = viewModelScope.launch {
        val item = withContext(Dispatchers.IO) {
            database.operationDao.getOperationById(id)
        }
        onResult(item)  // Возвращаем результат в главный поток
    }

//    fun updateCategoryId() {
//        viewModelScope.launch {
//            database.operationDao.updateCategoryIdFromZeroToOne()
//        }
//    }

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