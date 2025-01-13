package com.example.myfinance

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfinance.ui.MyNavigationDrawer
import com.example.myfinance.ui.theme.MyFinanceTheme
import com.example.myfinance.viewmodels.OperationViewModel
import com.example.myfinance.viewmodels.TitleViewModel

class MainActivity : ComponentActivity() {
    val titleViewModel: TitleViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // Обработка необработанного исключения
            val errorMessage = "Необработанное исключение: ${throwable.message}"
            println(errorMessage)
            Log.e("MyApp", errorMessage);
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
            }
        }



        enableEdgeToEdge()
        setContent {
            MyFinanceTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) {
//                    //MainScreen()
//                }
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigationDrawer(titleViewModel)
                }
            }
        }
    }
}

