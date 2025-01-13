package com.example.myfinance.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfinance.ui.screens.EditCategoryScreen
import com.example.myfinance.viewmodels.TitleViewModel
import com.example.myfinance.ui.screens.EditOperationScreen
import com.example.myfinance.ui.screens.MainScreen
import com.example.myfinance.ui.screens.Screens
import com.example.myfinance.ui.screens.StatisticScreen
import com.example.myfinance.ui.theme.Purple40
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavigationDrawer(titleViewModel: TitleViewModel){
    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier.fillMaxWidth().height(30.dp)){
                    Text(text = "What the wonderful menu")
                }
                HorizontalDivider()
                NavigationDrawerItem(label = { Text(text = "Главная", color = Purple40 ) },
                    selected = false,
                    icon = { Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Main",
                        tint = Purple40)
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Main.screen){
                            popUpTo(0)
                        }
                    })
                NavigationDrawerItem(label = { Text(text = "Статистика", color = Purple40 ) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Warning, contentDescription = "Statistic", tint = Purple40) },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Statistic.screen){
                            popUpTo(0)
                        }
                    })
                HorizontalDivider()
                NavigationDrawerItem(label = { Text(text = "Добавить Категорию", color = Purple40 ) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Warning, contentDescription = "Category", tint = Purple40) },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.AddCategory.screen){
                            //popUpTo(0)
                        }
                    })
            }
        }
    ) {
        Scaffold(
            topBar = {
                val coroutineScope = rememberCoroutineScope()
                TopAppBar(
                    title = { Text(text = titleViewModel.title) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple40,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Rounded.Menu, contentDescription = "MenuButton"
                            )
                        }
                    })
            }
        ) { paddingValues ->
            NavHost(
                navController = navigationController,
                startDestination = Screens.Main.screen,
                Modifier.padding(paddingValues)
            ){
                composable(Screens.Main.screen){ MainScreen(navigationController = navigationController) }
                composable(Screens.Statistic.screen){ StatisticScreen() }
                composable(Screens.AddOperation.screen){
                    EditOperationScreen(
                        navigationController = navigationController,
                        titleViewModel = titleViewModel
                    )
                }
                composable(Screens.EditOperation.screen){ backStackEntry ->
                    val entityId = backStackEntry.arguments?.getString("entityId")
                    EditOperationScreen(
                        navigationController = navigationController,
                        titleViewModel = titleViewModel,
                        newId = entityId?.toInt()
                    )
                }
                composable(Screens.AddCategory.screen){
                    EditCategoryScreen(
                        navigationController = navigationController,
                        titleViewModel = titleViewModel
                    )
                }
            }
        }
    }
}