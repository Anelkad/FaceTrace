package com.example.data.repository

import com.example.data.remote.FaceTraceApi
import com.example.domain.model.CommonResult
import com.example.domain.model.SearchResult
import com.example.domain.repository.FaceTraceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FaceTraceRepositoryImplTest(
    private val api: FaceTraceApi
) : FaceTraceRepository {
    override suspend fun search(path: String): CommonResult<List<SearchResult>> =
        withContext(Dispatchers.IO) {
            delay(1500)
            CommonResult(
                result = listOf(
                    SearchResult(
                        url = "https://facebotexapls.s3.eu-north-1.amazonaws.com/face_photos/photo_0681fc43-b2b7-458f-8cd7-237511618592.jpg",
                        prediction = 100
                    ),
                    SearchResult(
                        url = "https://facebotexapls.s3.eu-north-1.amazonaws.com/face_photos/photo_db9b1409-f837-497f-8e89-3e4345769028.jpg",
                        prediction = 63
                    ),
                    SearchResult(
                        url = "https://facebotexapls.s3.eu-north-1.amazonaws.com/face_photos/photo_0681fc43-b2b7-458f-8cd7-237511618592.jpg",
                        prediction = 100
                    ),
                    SearchResult(
                        url = "https://facebotexapls.s3.eu-north-1.amazonaws.com/face_photos/photo_db9b1409-f837-497f-8e89-3e4345769028.jpg",
                        prediction = 63
                    ),
                    SearchResult(
                        url = "https://facebotexapls.s3.eu-north-1.amazonaws.com/face_photos/photo_0681fc43-b2b7-458f-8cd7-237511618592.jpg",
                        prediction = 100
                    ),
                    SearchResult(
                        url = "https://facebotexapls.s3.eu-north-1.amazonaws.com/face_photos/photo_db9b1409-f837-497f-8e89-3e4345769028.jpg",
                        prediction = 63
                    )
                )
            )
        }
}