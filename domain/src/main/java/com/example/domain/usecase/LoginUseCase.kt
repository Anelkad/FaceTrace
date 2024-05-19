package com.example.domain.usecase

import com.example.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend fun execute(email: String, password: String) =
        repository.login(email = email, password = password)
}
