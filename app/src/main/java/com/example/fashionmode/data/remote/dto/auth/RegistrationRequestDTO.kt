package com.example.fashionmode.data.remote.dto.auth

data class RegistrationRequestDTO(
    val name: String,
    val email: String,
    val password: String
)