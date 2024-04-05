package com.example.facetrace.base

sealed class CommonState {
    object ShowLoading : CommonState()
    object HideLoading : CommonState()
    data class Error(val error: String) : CommonState()
    data class Result<out T : Any>(val result: T) : CommonState()
}