package com.example.myfinance.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfinance.viewmodels.OperationViewModel
import com.example.myfinance.ui.theme.MyFinanceTheme
import com.example.myfinance.ui.theme.Purple40
import com.example.myfinance.viewmodels.TitleViewModel
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun StatisticScreen (
    mainViewModel: OperationViewModel = viewModel(factory = OperationViewModel.factory),
    titleViewModel: TitleViewModel
){
    val itemsList = mainViewModel.itemsList.collectAsState(initial = emptyList()).value

    val totalAmount = itemsList.sumOf { it.amount }
    val roundedTotalAmount = BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP).toDouble()

    val positiveAmounts = itemsList.filter { it.amount >= 0 }
    val negativeAmounts = itemsList.filter { it.amount < 0 }

    val income = positiveAmounts.sumOf { it.amount }
    val expenses = negativeAmounts.sumOf { it.amount }

    LaunchedEffect(Unit) {
        titleViewModel.title = "Статистика"
    }

    Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Purple40, // Цвет фона карточки
                contentColor = Color.White
            )
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ){
                Text(
                    text = "Баланс",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        //.align(Alignment.Center)
                        .padding(5.dp)
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White, // Цвет фона карточки
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(
                        text = "${roundedTotalAmount}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            //.align(Alignment.Center)
                            .padding(5.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Доходы",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                //.align(Alignment.Center)
                                .padding(5.dp)
                        )
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White, // Цвет фона карточки
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .padding(5.dp)
                        ) {
                            Text(
                                text = "${income}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    //.align(Alignment.Center)
                                    .padding(5.dp)
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Расходы",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                //.align(Alignment.Center)
                                .padding(5.dp)
                        )
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White, // Цвет фона карточки
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .padding(5.dp)
                        ) {
                            Text(
                                text = "${expenses}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    //.align(Alignment.Center)
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }

        }

}
