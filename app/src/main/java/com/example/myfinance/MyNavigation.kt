package com.example.myfinance

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfinance.ui.screens.EditCategoryScreen
import com.example.myfinance.ui.screens.EditOperationScreen
import com.example.myfinance.ui.screens.LoginScreen
import com.example.myfinance.ui.screens.MainScreen
import com.example.myfinance.ui.screens.RegistrationScreen
import com.example.myfinance.ui.screens.Screens
import com.example.myfinance.ui.screens.StatisticScreen
import com.example.myfinance.ui.theme.Purple40
import com.example.myfinance.viewmodels.AuthViewModel
import com.example.myfinance.viewmodels.TitleViewModel
import com.google.android.gms.safetynet.SafetyNet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@Composable
fun MyNavigation() {
    val authViewModel = remember { AuthViewModel() }
    val titleViewModel = remember { TitleViewModel() }

    val navigationController = rememberNavController()

    if (authViewModel.isAuthenticated) {
        MyNavigationDrawer(navigationController, authViewModel, titleViewModel)
    } else {
        AuthNavHost(navigationController, authViewModel)
    }
}

@Composable
fun AuthNavHost(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Register.screen
    ) {
        composable(Screens.Login.screen) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    authViewModel.isAuthenticated = true
                },
                authViewModel = authViewModel
            )
        }
        composable(Screens.Register.screen) {
            RegistrationScreen(
                navController = navController,
                onRegisterSuccess = {
                    navController.navigate(Screens.Login.screen) {
                        popUpTo(Screens.Register.screen) { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavigationDrawer(
    navigationController: NavHostController,
    authViewModel: AuthViewModel,
    titleViewModel: TitleViewModel
){
    //val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier.fillMaxWidth().height(40.dp)){
                    Text(
                        text = "Привет, ${authViewModel.userName}!",
                        fontSize = 18.sp
                    )
                }
                HorizontalDivider()
                NavigationDrawerItem(label = { Text(text = "Главная", color = Purple40 ) },
                    selected = false,
                    icon = { Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Main",
                        tint = Purple40
                    )
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
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Statistic",
                            tint = Purple40
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Statistic.screen){
                            //popUpTo(0)
                        }
                    })
                if (authViewModel.isAdmin){
                    HorizontalDivider()
                    NavigationDrawerItem(label = { Text(text = "Добавить Категорию", color = Purple40 ) },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Category",
                                tint = Purple40
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            navigationController.navigate(Screens.AddCategory.screen){
                                //popUpTo(0)
                            }
                        }
                    )
                }
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
                    },
                    actions = {
                        IconButton(
                            onClick = { authViewModel.isAuthenticated = false }
                        )
                        {
                            Icon(
                                Icons.AutoMirrored.Rounded.ExitToApp,
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navigationController,
                startDestination = Screens.Main.screen,
                Modifier.padding(paddingValues)
            ){
                composable(Screens.Main.screen){
                    MainScreen(
                        navigationController = navigationController,
                        titleViewModel = titleViewModel
                    )
                }
                composable(Screens.Statistic.screen){
                    StatisticScreen(
                        titleViewModel = titleViewModel
                    )
                }
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



