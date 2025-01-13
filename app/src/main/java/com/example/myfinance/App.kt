package com.example.myfinance

import android.app.Application
import com.example.myfinance.data.MyDatabase

class App : Application() {
    val database by lazy { MyDatabase.createDatabase(this) }
}