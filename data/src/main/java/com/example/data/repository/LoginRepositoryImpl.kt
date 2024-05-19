package com.example.data.repository

import android.util.Log
import com.example.domain.model.CommonResult
import com.example.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : LoginRepository {
    override suspend fun login(email: String, password: String): CommonResult<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.d("qwerty", "login name: ${auth.currentUser?.displayName}")
            CommonResult(result = result.user)
        } catch (e: Exception) {
            e.printStackTrace()
            CommonResult(error = e.message)
        }
    }
}
