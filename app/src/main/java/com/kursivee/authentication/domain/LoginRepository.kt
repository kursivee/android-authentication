package com.kursivee.authentication.domain

import android.util.Log
import com.kursivee.authentication.data.*
import com.kursivee.authentication.data.response.AuthenticationResponse
import com.kursivee.authentication.data.response.AuthorizeResponse
import com.kursivee.authentication.data.response.NetworkResponse

class LoginRepository {
    private val loginService = LoginService()
    private val loginCache = LoginCache()

    suspend fun authenticate(username: String): NetworkResponse<AuthenticationResponse> =
        loginCache.authenticationCache?.also {
            Log.d(LoginRepository::class.java.name, "AUTHN FROM CACHE: $it")
        } ?: run {
            loginService.authenticate(username).also {
                Log.d(LoginRepository::class.java.name, "AUTHN TO CACHE: $it")
                loginCache.authenticationCache = it
            }
        }

    suspend fun authorize(username: String): NetworkResponse<AuthorizeResponse> =
        loginCache.authorizationCache?.also {
            Log.d(LoginRepository::class.java.name, "AUTHZ FROM CACHE: $it")
        } ?: run {
            loginService.authorize(username).also {
                Log.d(LoginRepository::class.java.name, "AUTHZ TO CACHE: $it")
                loginCache.authorizationCache = it
            }
        }
}