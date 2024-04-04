package com.example.domain.model

import java.io.Serializable

data class SearchResult(
    val url: String,
    val prediction: Int
): Serializable