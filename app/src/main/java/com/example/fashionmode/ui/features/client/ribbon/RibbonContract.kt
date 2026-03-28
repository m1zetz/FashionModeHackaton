package com.example.fashionmode.ui.features.client.ribbon

data class Product(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val size: String = "",
    val price: Long = 0
)

data class RibbonState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed class RibbonIntent {
    object LoadProducts : RibbonIntent()
    object UpdatePage : RibbonIntent()

    data class NavigateToDetails(val id: String) : RibbonIntent()
}


sealed class RibbonEffect {

    data class NavigateToDetails(val id: String) : RibbonEffect()
}