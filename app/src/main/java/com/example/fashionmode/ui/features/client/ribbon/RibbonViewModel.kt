package com.example.fashionmode.ui.features.client.ribbon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionmode.domain.repository.ProductRepository
import com.example.fashionmode.ui.features.client.ribbon.RibbonEffect.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RibbonViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _state = MutableStateFlow(RibbonState())
    val state = _state.asStateFlow()


    private val _channel = Channel<RibbonEffect>(Channel.BUFFERED)
    val channel = _channel.receiveAsFlow()

    init {
        handleIntent(RibbonIntent.LoadProducts)
    }

    fun handleIntent(intent: RibbonIntent) {
        when (intent) {
            is RibbonIntent.LoadProducts -> {
                loadProducts()
            }
            is RibbonIntent.UpdatePage -> {
                loadProducts()
            }

            is RibbonIntent.NavigateToDetails -> {
                viewModelScope.launch {
                    _channel.send(RibbonEffect.NavigateToDetails(intent.id))
                }

            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _state.update {state ->
                state.copy(isLoading = true)
            }
            val result = productRepository.getProducts()
            result.onSuccess { list ->
                _state.update {
                    it.copy(products = list, isLoading = false)
                }
            }.onFailure {error ->
                _state.update {state ->
                    state.copy(isLoading = false, error = error.message ?: "Ошибка сети")
                }
            }
        }
    }
}