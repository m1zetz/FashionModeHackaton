package com.example.fashionmode.data.repository

import android.util.Log
import com.example.fashionmode.common.enums.UserType
import com.example.fashionmode.domain.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<UserType> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()

            val uid = authResult.user?.uid ?: throw Exception("UID не найден")
            Log.d("id", uid)
            val snapshot = db.collection("users").document(uid).get().await()
            val typeString = snapshot.getString("type") ?: "CLIENT"
            val userType = UserType.valueOf(typeString.uppercase())
            Result.success(userType)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registration(
        name: String,
        email: String,
        password: String,
    ): Result<Unit> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("Не удалось получить UID")
            val userMap = mapOf(
                "uid" to uid,
                "name" to name,
                "type" to UserType.CLIENT.name,
                "email" to email
            )
            db.collection("users").document(uid).set(userMap).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}