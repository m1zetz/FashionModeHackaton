package com.example.fashionmode.ui.features.signin

import com.example.fashionmode.common.enums.UserType

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
    object NavigateToRegistration : SignInIntent()
}

sealed class SignInEffect{
    data class NavigateToMain(val type: UserType) : SignInEffect()
    object NavigateToRegistration : SignInEffect()
}