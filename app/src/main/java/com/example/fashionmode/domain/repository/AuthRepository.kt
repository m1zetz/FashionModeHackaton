package com.example.fashionmode.domain.repository

import com.example.fashionmode.common.enums.UserType

interface AuthRepository {

    suspend fun login(email: String, password: String) : Result<UserType>
    suspend fun registration(name: String, email: String, password: String) : Result<Unit>
}