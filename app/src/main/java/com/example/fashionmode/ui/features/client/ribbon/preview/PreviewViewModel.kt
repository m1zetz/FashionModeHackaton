package com.example.fashionmode.ui.features.client.ribbon.preview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionmode.domain.repository.ProductRepository
import com.example.fashionmode.ui.features.client.basket.BasketItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PreviewViewModel(
    private val productId: String,
    private val repository: ProductRepository,
    private val auth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {


    private val _state = MutableStateFlow(PreviewState())
    val state = _state.asStateFlow()

    private val _channel = Channel<PreviewEffect>(Channel.BUFFERED)
    val channel = _channel.receiveAsFlow()
    init {
        handleIntent(PreviewIntent.LoadProduct)
    }

    fun handleIntent(intent: PreviewIntent) {
        when (intent) {
            is PreviewIntent.LoadProduct -> loadProductDetails()
            is PreviewIntent.AddToBasket -> {  addToBasket()}
            PreviewIntent.OrderProduct -> {
                orderProduct()
            }
        }
    }

    private fun addToBasket() {
        val currentProduct = _state.value.product ?: return
        val userId = auth.currentUser?.uid

        if (userId == null) {
            _state.update { it.copy(error = "Пожалуйста, войдите в аккаунт") }
            return
        }

        viewModelScope.launch {
            val basketItem = BasketItem(
                id = currentProduct.id,
                title = currentProduct.title,
                price = currentProduct.price,
                imageUrl = currentProduct.imageUrl,
                size = currentProduct.size,
                quantity = 1
            )

            val result = repository.addToBasket(userId, basketItem)

            result.onSuccess {
                Log.d("BASKET", "Товар добавлен успешно")
            }.onFailure { e ->
                _state.update { it.copy(error = "Не удалось добавить в корзину") }
            }
        }
    }
    private fun orderProduct() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }


            val result = repository.getProductById(productId)

            result.onSuccess {
                _state.update { it.copy(isLoading = false) }



                _channel.send(PreviewEffect.NavigateBack)
            }.onFailure {
                _state.update { it.copy(isLoading = false, error = "Ошибка заказа") }
            }
        }
    }
    private fun loadProductDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }

            repository.getProductById(productId)
                .onSuccess { product ->
                    _state.update { it.copy(product = product, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(
                        isLoading = false,
                        error = error.message ?: "Не удалось загрузить товар"
                    ) }
                }
        }
    }
}