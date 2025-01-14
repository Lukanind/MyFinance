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
fun RegistrationScreen(
    viewModel: UserViewModel = viewModel(factory = UserViewModel.factory),
    navController: NavHostController,
    onRegisterSuccess: () -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var registrationSuccess by remember { mutableStateOf<Boolean?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40)
    ) {
        Text(
            "РЕГИСТРАЦИЯ",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
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
                    try {
                        viewModel.registerUser(userName, password) { success ->
                            registrationSuccess = success
                        }
                        if (registrationSuccess == true){
                            onRegisterSuccess()
                        } else {
                            Toast.makeText(context, "Такой пользователь уже существует!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (ex: Exception){
                        Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_SHORT).show()
                        Log.d("Register", ex.localizedMessage)
                    }
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
        Button(
            onClick = {
                navController.navigate(Screens.Login.screen){

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
                "Уже есть аккаунт",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}