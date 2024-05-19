package com.example.domain.repository

import com.example.domain.model.CommonResult
import com.google.firebase.auth.FirebaseUser

interface LoginRepository {
    suspend fun login(email: String, password: String): CommonResult<FirebaseUser>
}