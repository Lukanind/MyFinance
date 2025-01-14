package com.example.myfinance.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfinance.viewmodels.CategoryViewModel

@Composable
fun CategoryDropdownMenu(categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.factory)) {
    // Состояние для управления открытием/закрытием меню
    var expanded by remember { mutableStateOf(false) }
    // Состояние для выбранной категории
    var selectedCategory by remember { mutableStateOf("Выберите категорию") }

    // Получаем все категории из ViewModel
    val categories by categoryViewModel.allCategories.collectAsState(initial = emptyList())

    Column {
        // Отображаем выбранную категорию
        Text(
            text = selectedCategory,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
                .border(1.dp, Color.Gray)
                .fillMaxWidth(),
            fontSize = 20.sp
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
                        categoryViewModel.currentId.value = category.id
                        expanded = false
                    })
            }
        }
    }
}



