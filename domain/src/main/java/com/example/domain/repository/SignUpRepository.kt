package com.example.domain.repository

import com.example.domain.model.CommonResult
import com.google.firebase.auth.FirebaseUser

interface SignUpRepository {
    suspend fun signUp(email: String, fullname: String, password: String): CommonResult<FirebaseUser>
}