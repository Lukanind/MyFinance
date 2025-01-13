package com.example.myfinance.ui.screens

sealed class Screens(val screen: String) {
    data object Main: Screens("main")
    data object AddOperation: Screens("edit")
    data object EditOperation: Screens("edit/{entityId}")
    data object Statistic: Screens("statistic")
    data object AddCategory: Screens("addCategory")
}