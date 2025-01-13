package com.example.myfinance.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TitleViewModel : ViewModel() {
    var title by mutableStateOf("Мои операции")
}