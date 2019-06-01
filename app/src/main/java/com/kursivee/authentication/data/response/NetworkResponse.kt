package com.kursivee.authentication.data.response

data class NetworkResponse<T>(
    val response: T? = null,
    val error: ErrorResponse? = null
) {
    companion object {
        fun error(errorResponse: ErrorResponse?): NetworkResponse<ErrorResponse> {
            return NetworkResponse(null, errorResponse)
        }
    }
}