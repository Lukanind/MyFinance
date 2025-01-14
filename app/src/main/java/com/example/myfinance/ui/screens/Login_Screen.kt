package com.example.myfinance.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myfinance.ui.theme.Purple40
import com.example.myfinance.viewmodels.UserViewModel

@Composable
fun LoginScreen(
    viewModel: UserViewModel = viewModel(factory = UserViewModel.factory),
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginSuccess by remember { mutableStateOf<Boolean?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            "АВТОРИЗАЦИЯ",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Purple40,
            modifier = Modifier
                .padding(30.dp)
                .align(Alignment.CenterHorizontally)
        )
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Имя пользователя...") },
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            singleLine = true
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль...") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(40.dp))
        Button(
            onClick = {
                if (userName.isEmpty() || password.isEmpty()){
                    Toast.makeText(context, "Ошибка! Заполните поля!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.loginUser(userName, password) { user ->
                        if (user != null){
                            onLoginSuccess()
                        } else {
                            Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Text(
                "Войти",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = {
                navController.navigate(Screens.Register.screen){
                    popUpTo(0)
                }
            },

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Purple40
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Text(
                "Зарегистрироваться",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}