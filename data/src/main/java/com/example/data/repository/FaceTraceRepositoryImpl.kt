package com.example.data.repository

import com.example.data.model.ErrorResponse
import com.example.data.remote.FaceTraceApi
import com.example.domain.model.CommonResult
import com.example.domain.model.SearchResult
import com.example.domain.repository.FaceTraceRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
            "file",
            file.name.hashCode().toString() + ".jpg",
            file.asRequestBody(("image/jpeg").toMediaTypeOrNull())
        )
        val response = api.search(
            file = image
        )
        if (response.isSuccessful) {
            CommonResult(result = response.body()?.map { it.toDomain() })
        } else {
            val gson = Gson()
            val type = object : TypeToken<ErrorResponse>() {}.type
            val errorResponse: ErrorResponse? =
                gson.fromJson(response.errorBody()?.charStream(), type)
            CommonResult(error = errorResponse?.detail)
        }
    }
}