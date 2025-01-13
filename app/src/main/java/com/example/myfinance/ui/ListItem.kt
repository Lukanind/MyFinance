package com.example.myfinance.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfinance.data.OperationEntity

@Composable
fun ListItem(
    item: OperationEntity,
    onClick: (OperationEntity) -> Unit,
    onClickDelete: (OperationEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onClick(item)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val (icon, tint) = if (item.amount < 0) {
                Icons.Default.KeyboardArrowDown to Color.Red
            } else {
                Icons.Default.KeyboardArrowUp to Color.Green
            }
            Icon(
                imageVector = icon,
                contentDescription = "PlusOrMinus",
                tint = tint
            )
            Text(
                item.amount.toBigDecimal().toPlainString() + " ₽",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp)
            )
            Text(
                item.category,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp)
            )
            IconButton(
                onClick = {
                    onClickDelete(item)
                }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}