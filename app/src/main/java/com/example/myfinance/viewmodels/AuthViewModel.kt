package com.example.myfinance.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    var isAuthenticated by mutableStateOf(false)
    var isAdmin by mutableStateOf(false)
    var isSignIn by mutableStateOf(false)

    var userName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    val auth = Firebase.auth

    var taskText by mutableStateOf("")

    fun signUp(
        auth: FirebaseAuth,
        email: String,
        password: String,
        context: Context,
        onResult: (Boolean?) -> Unit
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("signUp", "createUserWithEmail:success")

                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(context, "Отправлено подтверждение почты на $email",  Toast.LENGTH_SHORT).show()
                                onResult(true)
                            } else {
                                Toast.makeText(context, "sendEmailVerification: failure",  Toast.LENGTH_SHORT).show()
                                onResult(false)
                            }
                        }
                } else {
                    Log.d("signUp", "createUserWithEmail:failure")
                    Toast.makeText(context, "Неудалось зарегистрировать пользователя",  Toast.LENGTH_SHORT).show()
                    onResult(false)
                }
            }
    }

    fun validatePassword(password: String): String {
        return when {
            password.length < 6 -> "Пароль должен содержать не менее 6 символов"
            else -> "" // Пароль валиден
        }
    }

    fun loginAndCheck(
        email: String,
        password: String,
        context: Context,
        onResult: (Boolean?) -> Unit
    ){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Log.d("loginAndCheck", "signInWithEmailAndPassword: success")

                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(context, "Успешная авторизация",  Toast.LENGTH_SHORT).show()
                        onResult(true)
                    } else {
                        Toast.makeText(context, "Пожалуйста, подтвердите почту!",  Toast.LENGTH_SHORT).show()
                        onResult(false)
                    }
                } else {
                    Log.d("loginAndCheck", "signInWithEmailAndPassword: failure")
                    Toast.makeText(context, "Не удалось авторизироваться",  Toast.LENGTH_SHORT).show()
                    onResult(false)
                }
            }
    }

}
