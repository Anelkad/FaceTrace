package com.example.domain.repository

import com.example.domain.model.CommonResult
import com.example.domain.model.SearchResult

interface FaceTraceRepository {
    suspend fun search(
        path: String
    ): CommonResult<List<SearchResult>>
}