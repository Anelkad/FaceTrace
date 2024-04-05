package com.example.domain.usecase

import com.example.domain.repository.FaceTraceRepository
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(private val repository: FaceTraceRepository) {
    suspend fun execute(path: String) = repository.search(path = path)
}