package com.example.myfinance.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myfinance.App
import com.example.myfinance.data.CategoryDao
import com.example.myfinance.data.CategoryEntity
import com.example.myfinance.data.MyDatabase
import com.example.myfinance.data.OperationEntity
import kotlinx.coroutines.launch

class CategoryViewModel(val database: MyDatabase) : ViewModel() {

    val allCategories = database.categoryDao.getAllCategories()

    var newEntity: CategoryEntity? = null
    val newCategory = mutableStateOf("")
    val currentId = mutableIntStateOf(0)

    fun insertItem() = viewModelScope.launch {
        val newItem = newEntity?.copy(
            categoryName = newCategory.value)
            ?: CategoryEntity(
                categoryName = newCategory.value
            )
        database.categoryDao.addCategory(newItem)
        newEntity = null
        newCategory.value = ""
    }

//    fun update(category: CategoryEntity) {
//        viewModelScope.launch {
//            database.categoryDao.update(category)
//        }
//    }

    fun delete(category: CategoryEntity) {
        viewModelScope.launch {
            database.categoryDao.deleteCategory(category)
        }
    }

    suspend fun getCategoryById(categoryId: Int): CategoryEntity? {
        return database.categoryDao.getCategoryById(categoryId)
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return CategoryViewModel(database) as T
            }
        }
    }

    fun logCategories() {
        viewModelScope.launch {
            allCategories.collect { categories ->
                Log.d("CategoryViewModel", "Categories: $categories")
            }
        }
    }

    init {
        logCategories()
    }



}
