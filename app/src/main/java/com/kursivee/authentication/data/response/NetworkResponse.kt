package com.kursivee.authentication.data.response

data class NetworkResponse<T>(
    val response: T? = null,
    val error: ErrorResponse? = null
)