package com.example.data.model

import com.example.domain.model.SearchResult
import java.io.Serializable

data class SearchResultDTO (
    val url: String,
    val prediction: Int
): Serializable {
    fun toDomain(): SearchResult = SearchResult(
        url = url,
        prediction = prediction
    )
}