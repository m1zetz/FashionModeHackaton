package com.example.fashionmode.ui.features.client.basket


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionmode.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BasketViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _state = MutableStateFlow(BasketState())
    val state = _state.asStateFlow()

    private val userId = "test_user"

    init {
        handleIntent(BasketIntent.LoadBasket)
    }

    fun handleIntent(intent: BasketIntent) {
        when (intent) {
            is BasketIntent.LoadBasket -> loadBasketItems()
            is BasketIntent.RemoveFromBasket -> removeItem(intent.productId)
            is BasketIntent.Checkout -> { /* Логика оплаты */ }
        }
    }

    private fun loadBasketItems() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = repository.getBasketItems(userId)
            result.onSuccess { list ->
                _state.update { it.copy(items = list, isLoading = false) }
            }.onFailure { e ->
                _state.update { it.copy(error = e.message ?: "Ошибка", isLoading = false) }
            }
        }
    }

    private fun removeItem(productId: String) {
        viewModelScope.launch {
            repository.removeFromBasket(userId, productId)
            loadBasketItems()
        }
    }
}