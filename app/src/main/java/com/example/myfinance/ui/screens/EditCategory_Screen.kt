package com.example.myfinance.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myfinance.ui.CategoryDropdownMenu
import com.example.myfinance.viewmodels.OperationViewModel
import com.example.myfinance.viewmodels.TitleViewModel
import com.example.myfinance.ui.theme.Purple40
import com.example.myfinance.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen(
    mainViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.factory),
    navigationController: NavHostController,
    titleViewModel: TitleViewModel
) {
    // Обновить заголовок при входе на экран
    LaunchedEffect(Unit) {
        titleViewModel.title = "Редактор категорий"
    }

    val context = LocalContext.current.applicationContext

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = mainViewModel.newCategory.value,
            onValueChange = {
                mainViewModel.newCategory.value = it
            },
            label = {
                Text(text = "Категория...")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xffeeeeee),
                unfocusedTextColor = Color(0xff888888),
                focusedContainerColor = Color.White,
                focusedTextColor = Color(0xff222222),
            ),
            singleLine = true
        )
        //Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Button(
                onClick = {
                    try{
                        mainViewModel.insertItem()
                        Toast.makeText(context, "Добавлена новая категория", Toast.LENGTH_LONG).show()
                        //navigationController.popBackStack()
                    } catch (ex: Exception){
                        Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Добавить",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        HorizontalDivider()
        CategoryDropdownMenu()
    }

}