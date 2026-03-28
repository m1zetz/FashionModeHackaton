package com.example.fashionmode.ui.features.client.Main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModelClient : ViewModel() {
    private val _screenIndexState = MutableStateFlow(MainUiState())
    val screenIndexState = _screenIndexState.asStateFlow()

    fun handleIntent(intent: MainIntentClient){
        when(intent){
            is MainIntentClient.ChangeScreen -> {
                _screenIndexState.update {
                    it.copy(selectedIndex = intent.index)
                }
            }
        }
    }

}