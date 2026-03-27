package com.example.fashionmode.domain.repository

interface AuthRepository {

    suspend fun login(email: String, password: String) : Result<Unit>
    suspend fun registration(name: String, email: String, password: String) : Result<Unit>
}