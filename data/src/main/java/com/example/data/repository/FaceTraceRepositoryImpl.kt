package com.example.data.repository

import com.example.data.remote.FaceTraceApi
import com.example.domain.model.CommonResult
import com.example.domain.model.SearchResult
import com.example.domain.repository.FaceTraceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FaceTraceRepositoryImpl(
    private val api: FaceTraceApi
) : FaceTraceRepository {
    override suspend fun search(path: String): CommonResult<List<SearchResult>> = withContext(Dispatchers.IO) {
        val file = File(path)
        val image = MultipartBody.Part.createFormData(
            "image",
            file.name,
            file.asRequestBody(("image/*").toMediaTypeOrNull())
        )
        val response = api.search(
            image = image
        )
        if (response.isSuccessful) {
            CommonResult(result = response.body()?.map { it.toDomain() })
        } else {
            CommonResult(error = response.message())
        }
    }
}