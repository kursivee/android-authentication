package com.kursivee.authentication.data

import com.kursivee.authentication.data.response.AuthetnicateResponse
import com.kursivee.authentication.data.response.AuthorizeResponse
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import com.kursivee.authentication.util.Usernames
import kotlinx.coroutines.delay

class LoginService {
    suspend fun authenticate(username: String): NetworkResponse<AuthetnicateResponse> {
        delay(100)
        if(Usernames.FAIL_AUTHN == Usernames.valueOf(username)) {
            return NetworkResponse(null, ErrorResponse("FAILED AUTHENTICATE"))
        }
        return NetworkResponse(AuthetnicateResponse())
    }

    suspend fun authorize(username: String): NetworkResponse<AuthorizeResponse> {
        delay(100)
        if(Usernames.FAIL_AUTHZ == Usernames.valueOf(username)) {
            return NetworkResponse(null, ErrorResponse("FAILED AUTHORIZE"))
        }
        return NetworkResponse(AuthorizeResponse())
    }
}