package com.example.domain.usecase

import com.example.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: SignUpRepository) {
    suspend fun execute(email: String, fullname: String, password: String) =
        repository.signUp(email = email, fullname = fullname, password = password)
}
