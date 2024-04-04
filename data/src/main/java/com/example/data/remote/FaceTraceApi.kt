package com.example.data.remote

import com.example.domain.model.SearchResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FaceTraceApi {
    @Multipart
    @POST("api/v1/search")
    suspend fun search(
        @Part image: MultipartBody.Part
    ): Response<List<SearchResult>>
}