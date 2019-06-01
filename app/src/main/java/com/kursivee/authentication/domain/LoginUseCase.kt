package com.kursivee.authentication.domain

import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse

class LoginUseCase {
    private val loginRepository = LoginRepository()

    suspend fun login(username: String): NetworkResponse<ErrorResponse> {
        authenticate(username)?.let {
            return NetworkResponse(it)
        }
        authorize(username)?.let {
            return NetworkResponse(it)
        }
        return NetworkResponse.error(null)
    }

    private suspend fun authenticate(username: String): ErrorResponse? =
        loginRepository.authenticate(username).error

    private suspend fun authorize(username: String): ErrorResponse? =
        loginRepository.authorize(username).error
}