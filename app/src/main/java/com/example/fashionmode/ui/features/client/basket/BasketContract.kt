package com.example.fashionmode.ui.features.client.basket

data class BasketItem(
    val id: String = "",
    val title: String = "",
    val price: Long = 0,
    val imageUrl: String = "",
    val size: String = "",
    val quantity: Int = 1
)

data class BasketState(
    val items: List<BasketItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed class BasketIntent {
    object LoadBasket : BasketIntent()
    data class RemoveFromBasket(val productId: String) : BasketIntent()
    object Checkout : BasketIntent()
}