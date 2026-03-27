package com.example.fashionmode.ui.features.signup

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionmode.domain.repository.AuthRepository


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignUpViewModel (
    private val authRepository: AuthRepository
) : ViewModel(){

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _channel = Channel<SignUpEffect>(Channel.BUFFERED)
    val channel = _channel.receiveAsFlow()

    fun handleIntent(intent: SignUpIntent){
        when(intent){
            is SignUpIntent.ChangeName -> {
                _state.update { state ->
                    state.copy(name = intent.name, error = "")
                }
            }
            is SignUpIntent.ChangeEmail -> {
                _state.update { state ->
                    state.copy(email = intent.email, error = "")
                }
            }
            is SignUpIntent.ChangePassword -> {
                _state.update { state ->
                    state.copy(password = intent.password, error = "")
                }
            }
            SignUpIntent.Registration -> {
                viewModelScope.launch {
                    authRepository.registration(_state.value.name,_state.value.email, _state.value.password)
                        .onSuccess {
                            _channel.send(SignUpEffect.NavigateToMain)
                        }.onFailure {
                            _state.update {state ->
                                state.copy(
                                    error = "Ошибка регистрации"
                                )
                            }
                        }
                }

            }

        }
    }
}
