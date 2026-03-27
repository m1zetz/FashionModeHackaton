package com.example.fashionmode.ui.features.signin

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed class SignInIntent{
    data class ChangeEmail(val email: String) : SignInIntent()
    data class ChangePassword(val password: String) : SignInIntent()
    object Login : SignInIntent()
}

sealed class SignInEffect{
    object NavigateToMain : SignInEffect()
}