package com.kursivee.authentication.domain

import com.kursivee.authentication.data.response.AuthenticationResponse
import com.kursivee.authentication.data.response.AuthorizeResponse
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import com.kursivee.authentication.util.Usernames
import kotlinx.coroutines.delay

class LoginClient {
    suspend fun authenticate(username: String): NetworkResponse<AuthenticationResponse> {
        delay(1000)
        return if(Usernames.FAIL_AUTHN == Usernames.valueOf(username)) {
            NetworkResponse(null, ErrorResponse("FAILED AUTHENTICATE"))
        } else {
            NetworkResponse(AuthenticationResponse())
        }
    }

    suspend fun authorize(username: String): NetworkResponse<AuthorizeResponse> {
        delay(1000)
        return if(Usernames.SUCCESS == Usernames.valueOf(username)) {
            NetworkResponse(AuthorizeResponse())
        } else {
            NetworkResponse(null, ErrorResponse("FAILED AUTHORIZE"))
        }
    }
}