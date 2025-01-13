package com.example.myfinance.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfinance.data.CategoryEntity
import com.example.myfinance.viewmodels.CategoryViewModel
import com.example.myfinance.viewmodels.OperationViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CategoryDropdown(viewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.factory)) {
//    val testCategories = listOf(
//        CategoryEntity(id = 1, categoryName = "Продукты"),
//        CategoryEntity(id = 2, categoryName = "Тест"),
//        CategoryEntity(id = 3, categoryName = "Тест2")
//    )
//
//    val categories by remember { mutableStateOf(testCategories) }
//
//
//    //val categories by viewModel.allCategories.collectAsState(initial = emptyList())
//    Log.d("CategoryDropdown", "Categories: ${categories.size}")
//    Log.d("CategoryDropdown", "Loaded categories: $categories")
//
//
//
//    var expanded by remember {
//        mutableStateOf(false)
//    }
//    var selectedCategory by remember {
//        mutableStateOf<CategoryEntity?>(null)
//    }
//
//    Log.d("CategoryDropdown", "Expanded: $expanded")
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp)
//            .clickable {
//                Log.d("CategoryDropdown", "Column clicked")
//            },
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        ExposedDropdownMenuBox(
//            expanded = expanded,
//            onExpandedChange = {
//                expanded = !expanded
//                Log.d("CategoryDropdown", "Dropdown expanded: $expanded")
//                Log.d("CategoryDropdown", "onExpandedChange triggered")
//
//            }
//        ) {
//            TextField(
//                value = selectedCategory?.categoryName ?: "",
//                onValueChange = { },
//                readOnly = true,
//                label = { Text("Выберите категорию") },
//                trailingIcon = {
//                    //Icon(Icons.Default.ArrowDropDown, contentDescription = null)
//                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//                    Log.d("CategoryDropdown", "Expanded: $expanded")
//                },
//                modifier = Modifier
//                    .clickable {
//                    expanded = !expanded
//                    Log.d("CategoryDropdown", "TextField clicked: Dropdown expanded: $expanded")
//                }
//                    .focusable(false)
//            )
//            ExposedDropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }
//            ) {
//                if (categories.isEmpty()) {
//                    DropdownMenuItem(
//                        text = { Text("Нет доступных категорий") },
//                        onClick = {},
//                        enabled = false
//                    )
//                } else {
//                    categories.forEach { category ->
//                        DropdownMenuItem(
//                            text = {
//                                Text(category.categoryName)
//                            },
//                            onClick = {
//                                selectedCategory = category
//                                expanded = false
//                                Log.d("CategoryDropdown", "Expanded: $expanded")
//                            },
//                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun CategoryDropdownMenu(viewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.factory)) {
    // Состояние для управления открытием/закрытием меню
    var expanded by remember { mutableStateOf(false) }
    // Состояние для выбранной категории
    var selectedCategory by remember { mutableStateOf("Выберите категорию") }

    // Получаем все категории из ViewModel
    val categories by viewModel.allCategories.collectAsState(initial = emptyList())

    Column {
        // Отображаем выбранную категорию
        Text(
            text = selectedCategory,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
                .border(1.dp, Color.Gray)
                .fillMaxWidth()
        )

        // Выпадающее меню
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {Text(text = category.categoryName)},
                    onClick = {
                        selectedCategory = category.categoryName
                        //viewModel.newCategory.value = selectedCategory // Обновляем значение в ViewModel
                        expanded = false
                    })
            }
        }
    }
}



