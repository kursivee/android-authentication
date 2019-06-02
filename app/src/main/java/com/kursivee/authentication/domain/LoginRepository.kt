package com.kursivee.authentication.domain

import android.util.Log
import com.kursivee.authentication.data.response.AuthenticationResponse
import com.kursivee.authentication.data.response.AuthorizeResponse
import com.kursivee.authentication.data.response.NetworkResponse

class LoginRepository(private val loginService: LoginClient, private val loginCache: LoginCache) {
    suspend fun authenticate(username: String): NetworkResponse<AuthenticationResponse> =
        loginCache.getAuthnResponse() ?: run {
            loginService.authenticate(username).also { response ->
                response.response?.let {
                    Log.d(LoginRepository::class.java.name, "AUTHN TO CACHE: $response")
                    loginCache.putAuthnResponse(response)
                } ?: run {
                    Log.d(LoginRepository::class.java.name, "FAILED RESPONSE NO CACHE")
                }
            }
        }


    suspend fun authorize(username: String): NetworkResponse<AuthorizeResponse> =
        loginCache.getAuthzResponse() ?: run {
            loginService.authorize(username).also { response ->
                response.response?.let {
                    Log.d(LoginRepository::class.java.name, "AUTHZ TO CACHE: $response")
                    loginCache.putAuthzResponse(response)
                } ?: run {
                    Log.d(LoginRepository::class.java.name, "FAILED RESPONSE NO CACHE")
                }
            }
        }
}