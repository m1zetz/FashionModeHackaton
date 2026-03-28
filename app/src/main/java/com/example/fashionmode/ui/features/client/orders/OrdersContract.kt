package com.example.fashionmode.ui.features.client.orders

data class Order(
    val id: String,
    val date: String,
    val price: String,
    val isDelivered: Boolean
)