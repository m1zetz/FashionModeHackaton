package com.example.fashionmode.ui.features.client.orders

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Orders() {
    val orders = listOf(
        Order("№99284", "Сегодня", "165.00$", false),
        Order("№98120", "24 Марта", "45.00$", true),
        Order("№97005", "15 Марта", "210.00$", true)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                "Заказы",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(orders) { order ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(order.id, fontWeight = FontWeight.SemiBold)
                    Text(order.date, fontSize = 12.sp, color = Color.Gray)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(order.price, fontWeight = FontWeight.Bold)
                    Text(
                        text = if (order.isDelivered) "Доставлено" else "В пути",
                        fontSize = 11.sp,
                        color = if (order.isDelivered) Color(0xFF27AE60) else Color(0xFFF2994A)
                    )
                }
            }
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
        }
    }
}