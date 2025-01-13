package com.example.myfinance.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myfinance.viewmodels.OperationViewModel
import com.example.myfinance.ui.ListItem
import com.example.myfinance.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: OperationViewModel = viewModel(factory = OperationViewModel.factory),
    navigationController: NavHostController,
) {
    //val itemsList = mainViewModel.itemsList.collectAsState(initial = emptyList())

    val itemsList = mainViewModel.operationsWithCategories.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Card(
                onClick = {
                    navigationController.navigate(Screens.AddOperation.screen)
                },
                modifier = Modifier
                    .padding(5.dp)
                    .width(100.dp)
                    .height(40.dp)
                    .align(Alignment.Center),
                colors = CardDefaults.cardColors(
                    containerColor = Purple40
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(itemsList.value.reversed()) { item ->
                ListItem(
                    item, {
//                        mainViewModel.newEntity = it
//                        mainViewModel.newAmount.doubleValue = it.amount
//                        mainViewModel.newCategory.value = it.category
//                        mainViewModel.newDescription.value = it.description.toString()
                        navigationController.navigate(//Screens.Add.screen +
                                "edit/${it.operationId}")
                    },
                    {
                        mainViewModel.deleteItem(it)
                    }
                )
            }
        }
    }
}
