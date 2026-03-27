package com.example.fashionmode.data.repository

import com.example.fashionmode.domain.repository.AuthRepository

class AuthRepositoryImpl() : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun registration(
        name: String,
        email: String,
        password: String,
    ): Result<Unit> {
        return Result.failure(Exception())
    }
}