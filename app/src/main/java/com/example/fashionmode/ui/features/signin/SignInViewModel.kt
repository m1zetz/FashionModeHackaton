package com.example.fashionmode.ui.features.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionmode.domain.repository.AuthRepository
import com.example.fashionmode.ui.features.signup.SignUpEffect
import com.example.fashionmode.ui.features.signup.SignUpIntent
import com.example.fashionmode.ui.features.signup.SignUpState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _channel = Channel<SignInEffect>(Channel.BUFFERED)
    val channel = _channel.receiveAsFlow()

    fun handleIntent(intent: SignInIntent) {
        when (intent) {

            is SignInIntent.ChangeEmail -> {
                _state.update { state ->
                    state.copy(email = intent.email, error = "")
                }
            }

            is SignInIntent.ChangePassword -> {
                _state.update { state ->
                    state.copy(password = intent.password, error = "")
                }
            }

            SignInIntent.Login -> {
                viewModelScope.launch {
                    authRepository.login(_state.value.email, _state.value.password)
                        .onSuccess {
                            _channel.send(SignInEffect.NavigateToMain)
                        }.onFailure {
                            _state.update {state ->
                                state.copy(
                                    error = "Ошибка авторизации"
                                )
                            }
                        }
                }

            }
        }
    }
}