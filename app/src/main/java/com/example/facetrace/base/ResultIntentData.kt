package com.example.facetrace.base

import com.example.domain.model.SearchResult
import java.io.Serializable

data class ResultIntentData(
    val result: List<SearchResult>
) : Serializable
