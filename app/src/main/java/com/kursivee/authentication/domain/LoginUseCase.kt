package com.kursivee.authentication.domain

import android.util.Log
import com.kursivee.authentication.data.db.entity.User
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse

class LoginUseCase(private val loginRepository: LoginRepository, private val userCache: UserDaoUseCase) {

    suspend fun login(username: String): NetworkResponse<ErrorResponse> {
        authenticate(username)?.let { return NetworkResponse(it) }
        authorize(username)?.let { return NetworkResponse(it) }
        getOrCreateUser(username)
        return NetworkResponse(null)
    }

    private suspend fun authenticate(username: String): ErrorResponse? =
        loginRepository.authenticate(username).error

    private suspend fun authorize(username: String): ErrorResponse? =
        loginRepository.authorize(username).error

    private fun getOrCreateUser(username: String): User =
        userCache.findByUsername(username)?.also {
            Log.d(LoginUseCase::class.java.name, "FOUND USER: $it")
        } ?: run {
            User(username, "FIRST").also {
                userCache.cache(it)
                Log.d(LoginUseCase::class.java.name, "CACHE USER: $it")
            }
        }
}