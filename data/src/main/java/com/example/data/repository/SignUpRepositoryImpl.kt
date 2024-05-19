package com.example.data.repository

import android.util.Log
import com.example.domain.model.CommonResult
import com.example.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : SignUpRepository {
    override suspend fun signUp(
        email: String,
        fullname: String,
        password: String
    ): CommonResult<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser

            val profileUpdates = userProfileChangeRequest {
                displayName = fullname
            }

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("qwerty", "User profile updated.")
                        Log.d("qwerty", "displayName: ${auth.currentUser?.displayName}")
                    }
                }
            CommonResult(result = result.user)
        } catch (e: Exception) {
            e.printStackTrace()
            CommonResult(error = e.message)
        }
    }
}
