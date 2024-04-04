package com.example.domain.model

data class CommonResult<out T : Any>(
    val result: T? = null,
    val error: String? = null
)