package com.example.data.remote

import com.example.data.model.SearchResultDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FaceTraceApi {
    @Multipart
    @POST("api/v1/search/")
    suspend fun search(
        @Part file: MultipartBody.Part
    ): Response<List<SearchResultDTO>>
}