package com.example.fashionmode.ui.features.client.Main

data class MainUiState(
    val selectedIndex: Int = 0
)
sealed class MainIntentClient{
    data class ChangeScreen(val index: Int) : MainIntentClient()
}