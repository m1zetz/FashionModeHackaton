package com.example.fashionmode.ui.features.signup

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed class SignUpIntent{
    data class ChangeName(val name: String) : SignUpIntent()
    data class ChangeEmail(val email: String) : SignUpIntent()
    data class ChangePassword(val password: String) : SignUpIntent()
    object Registration : SignUpIntent()
}

sealed class SignUpEffect{
    object NavigateBack : SignUpEffect()
    object NavigateToMain : SignUpEffect()
}